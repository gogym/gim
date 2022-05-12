"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const tslib_1 = require("tslib");
const AbsMessageHandler_1 = (0, tslib_1.__importDefault)(require("../AbsMessageHandler"));
const messageBody_1 = (0, tslib_1.__importDefault)(require("../../../../db/model/messageBody"));
const class_transformer_1 = require("class-transformer");
const store_1 = (0, tslib_1.__importDefault)(require("../../../store"));
class MsgHandler extends AbsMessageHandler_1.default {
    handler(messagePb) {
        let messageBody = (0, class_transformer_1.plainToClass)(messageBody_1.default, JSON.parse(messagePb.body));
        return store_1.default.dispatch("message/handlerMsg", messageBody);
    }
}
exports.default = MsgHandler;
//# sourceMappingURL=MsgHandler.js.map