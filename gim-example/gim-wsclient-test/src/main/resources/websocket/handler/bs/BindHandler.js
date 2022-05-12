"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const tslib_1 = require("tslib");
const AbsMessageHandler_1 = (0, tslib_1.__importDefault)(require("../AbsMessageHandler"));
class BindHandler extends AbsMessageHandler_1.default {
    handler(messagePb) {
        console.log('绑定信息');
    }
}
exports.default = BindHandler;
//# sourceMappingURL=BindHandler.js.map