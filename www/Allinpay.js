var exec = require('cordova/exec');

exports.pay = function(payData, success, error) {
    exec(success, error, "Allinpay", "pay", [payData]);
};
