var console = jsmanager.console;
function type(code) {jsmanager.keyPress(code)};
function press(code) {jsmanager.keyPress(code, true)};
function release(code) {jsmanager.keyPress(code, false)};

// Adopted from here: https://gist.github.com/bripkens/8597903
// Makes ES7 Promises polyfill work on Nashorn https://github.com/jakearchibald/es6-promise
// (Haven't verified how correct it is, use with care)
(function(context) {
  'use strict';

  var Timer = Java.type('java.util.Timer');
  var Phaser = Java.type('java.util.concurrent.Phaser');

  var timer = new Timer('jsEventLoop', false);
  var phaser = new Phaser();

  var onTaskFinished = function() {
    phaser.arriveAndDeregister();
  };

  var timeoutStack = 0;
  /*
  function tryShutdown() {
    if (timeoutStack > 0) {
      return;
    }
    timer.cancel();
    phaser.forceTermination();
  }
  */

  var allTask = [];
  context.clearAll = function() {
    allTask.forEach(function( elem, index, ary ){
      elem();
    });
  }

  context.setTimeout = function(fn, millis /* [, args...] */) {
    var args = [].slice.call(arguments, 2, arguments.length);

    timeoutStack++;

    var phase = phaser.register();
    var canceled = false;
    timer.schedule(function() {
      if (canceled) {
        return;
      }

      try {
        fn.apply(context, args);
        timeoutStack--;
      } catch (e) {
        print(e);
      } finally {
        onTaskFinished();
        // tryShutdown();
      }
    }, millis);

    var cancelCall = function() {
      onTaskFinished();
      canceled = true;
    };
    allTask.push(cancelCall);
    return cancelCall;
  };

  context.clearTimeout = function(cancel) {
    cancel();
  };

  context.setInterval = function(fn, delay /* [, args...] */) {
    var args = [].slice.call(arguments, 2, arguments.length);

    var cancel = null;

    var loop = function() {
      cancel = context.setTimeout(loop, delay);
      fn.apply(context, args);
    };

    cancelCall = context.setTimeout(loop, delay);
    var cancelCall = function() {
      cancel();
    };
    allTask.push(cancelCall);
    return cancelCall;
  };

  context.clearInterval = function(cancel) {
    cancel();
  };

})(this);

function onDispose() {
	clearAll();
}