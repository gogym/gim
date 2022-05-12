/*eslint-disable block-scoped-var, id-length, no-control-regex, no-magic-numbers, no-prototype-builtins, no-redeclare, no-shadow, no-var, sort-vars*/
"use strict";

var $protobuf = require("protobufjs/minimal");

// Common aliases
var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;

// Exported root namespace
var $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});

$root.Message = (function() {

    /**
     * Properties of a MessageEntity.
     * @exports IMessage
     * @interface IMessage
     * @property {string|null} [id] MessageEntity id
     * @property {string|null} [serverId] MessageEntity serverId
     * @property {number|null} [reqType] MessageEntity reqType
     * @property {number|Long|null} [msgTime] MessageEntity msgTime
     * @property {number|null} [syn] MessageEntity syn
     * @property {string|null} [ack] MessageEntity ack
     * @property {string|null} [fromId] MessageEntity fromId
     * @property {string|null} [toId] MessageEntity toId
     * @property {string|null} [groupId] MessageEntity groupId
     * @property {string|null} [body] MessageEntity body
     * @property {number|null} [status] MessageEntity status
     */

    /**
     * Constructs a new MessageEntity.
     * @exports MessageEntity
     * @classdesc Represents a MessageEntity.
     * @implements IMessage
     * @constructor
     * @param {IMessage=} [properties] Properties to set
     */
    function Message(properties) {
        if (properties)
            for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                if (properties[keys[i]] != null)
                    this[keys[i]] = properties[keys[i]];
    }

    /**
     * MessageEntity id.
     * @member {string} id
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.id = "";

    /**
     * MessageEntity serverId.
     * @member {string} serverId
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.serverId = "";

    /**
     * MessageEntity reqType.
     * @member {number} reqType
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.reqType = 0;

    /**
     * MessageEntity msgTime.
     * @member {number|Long} msgTime
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.msgTime = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

    /**
     * MessageEntity syn.
     * @member {number} syn
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.syn = 0;

    /**
     * MessageEntity ack.
     * @member {string} ack
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.ack = "";

    /**
     * MessageEntity fromId.
     * @member {string} fromId
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.fromId = "";

    /**
     * MessageEntity toId.
     * @member {string} toId
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.toId = "";

    /**
     * MessageEntity groupId.
     * @member {string} groupId
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.groupId = "";

    /**
     * MessageEntity body.
     * @member {string} body
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.body = "";

    /**
     * MessageEntity status.
     * @member {number} status
     * @memberof MessageEntity
     * @instance
     */
    Message.prototype.status = 0;

    /**
     * Creates a new MessageEntity instance using the specified properties.
     * @function create
     * @memberof MessageEntity
     * @static
     * @param {IMessage=} [properties] Properties to set
     * @returns {Message} MessageEntity instance
     */
    Message.create = function create(properties) {
        return new Message(properties);
    };

    /**
     * Encodes the specified MessageEntity message. Does not implicitly {@link Message.verify|verify} messages.
     * @function encode
     * @memberof MessageEntity
     * @static
     * @param {IMessage} message MessageEntity message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    Message.encode = function encode(message, writer) {
        if (!writer)
            writer = $Writer.create();
        if (message.id != null && Object.hasOwnProperty.call(message, "id"))
            writer.uint32(/* id 1, wireType 2 =*/10).string(message.id);
        if (message.serverId != null && Object.hasOwnProperty.call(message, "serverId"))
            writer.uint32(/* id 2, wireType 2 =*/18).string(message.serverId);
        if (message.reqType != null && Object.hasOwnProperty.call(message, "reqType"))
            writer.uint32(/* id 3, wireType 0 =*/24).int32(message.reqType);
        if (message.msgTime != null && Object.hasOwnProperty.call(message, "msgTime"))
            writer.uint32(/* id 4, wireType 0 =*/32).int64(message.msgTime);
        if (message.syn != null && Object.hasOwnProperty.call(message, "syn"))
            writer.uint32(/* id 5, wireType 0 =*/40).int32(message.syn);
        if (message.ack != null && Object.hasOwnProperty.call(message, "ack"))
            writer.uint32(/* id 6, wireType 2 =*/50).string(message.ack);
        if (message.fromId != null && Object.hasOwnProperty.call(message, "fromId"))
            writer.uint32(/* id 7, wireType 2 =*/58).string(message.fromId);
        if (message.toId != null && Object.hasOwnProperty.call(message, "toId"))
            writer.uint32(/* id 8, wireType 2 =*/66).string(message.toId);
        if (message.groupId != null && Object.hasOwnProperty.call(message, "groupId"))
            writer.uint32(/* id 9, wireType 2 =*/74).string(message.groupId);
        if (message.body != null && Object.hasOwnProperty.call(message, "body"))
            writer.uint32(/* id 10, wireType 2 =*/82).string(message.body);
        if (message.status != null && Object.hasOwnProperty.call(message, "status"))
            writer.uint32(/* id 11, wireType 0 =*/88).int32(message.status);
        return writer;
    };

    /**
     * Encodes the specified MessageEntity message, length delimited. Does not implicitly {@link Message.verify|verify} messages.
     * @function encodeDelimited
     * @memberof MessageEntity
     * @static
     * @param {IMessage} message MessageEntity message or plain object to encode
     * @param {$protobuf.Writer} [writer] Writer to encode to
     * @returns {$protobuf.Writer} Writer
     */
    Message.encodeDelimited = function encodeDelimited(message, writer) {
        return this.encode(message, writer).ldelim();
    };

    /**
     * Decodes a MessageEntity message from the specified reader or buffer.
     * @function decode
     * @memberof MessageEntity
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @param {number} [length] MessageEntity length if known beforehand
     * @returns {Message} MessageEntity
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    Message.decode = function decode(reader, length) {
        if (!(reader instanceof $Reader))
            reader = $Reader.create(reader);
        var end = length === undefined ? reader.len : reader.pos + length, message = new $root.Message();
        while (reader.pos < end) {
            var tag = reader.uint32();
            switch (tag >>> 3) {
            case 1:
                message.id = reader.string();
                break;
            case 2:
                message.serverId = reader.string();
                break;
            case 3:
                message.reqType = reader.int32();
                break;
            case 4:
                message.msgTime = reader.int64();
                break;
            case 5:
                message.syn = reader.int32();
                break;
            case 6:
                message.ack = reader.string();
                break;
            case 7:
                message.fromId = reader.string();
                break;
            case 8:
                message.toId = reader.string();
                break;
            case 9:
                message.groupId = reader.string();
                break;
            case 10:
                message.body = reader.string();
                break;
            case 11:
                message.status = reader.int32();
                break;
            default:
                reader.skipType(tag & 7);
                break;
            }
        }
        return message;
    };

    /**
     * Decodes a MessageEntity message from the specified reader or buffer, length delimited.
     * @function decodeDelimited
     * @memberof MessageEntity
     * @static
     * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
     * @returns {Message} MessageEntity
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    Message.decodeDelimited = function decodeDelimited(reader) {
        if (!(reader instanceof $Reader))
            reader = new $Reader(reader);
        return this.decode(reader, reader.uint32());
    };

    /**
     * Verifies a MessageEntity message.
     * @function verify
     * @memberof MessageEntity
     * @static
     * @param {Object.<string,*>} message Plain object to verify
     * @returns {string|null} `null` if valid, otherwise the reason why it is not
     */
    Message.verify = function verify(message) {
        if (typeof message !== "object" || message === null)
            return "object expected";
        if (message.id != null && message.hasOwnProperty("id"))
            if (!$util.isString(message.id))
                return "id: string expected";
        if (message.serverId != null && message.hasOwnProperty("serverId"))
            if (!$util.isString(message.serverId))
                return "serverId: string expected";
        if (message.reqType != null && message.hasOwnProperty("reqType"))
            if (!$util.isInteger(message.reqType))
                return "reqType: integer expected";
        if (message.msgTime != null && message.hasOwnProperty("msgTime"))
            if (!$util.isInteger(message.msgTime) && !(message.msgTime && $util.isInteger(message.msgTime.low) && $util.isInteger(message.msgTime.high)))
                return "msgTime: integer|Long expected";
        if (message.syn != null && message.hasOwnProperty("syn"))
            if (!$util.isInteger(message.syn))
                return "syn: integer expected";
        if (message.ack != null && message.hasOwnProperty("ack"))
            if (!$util.isString(message.ack))
                return "ack: string expected";
        if (message.fromId != null && message.hasOwnProperty("fromId"))
            if (!$util.isString(message.fromId))
                return "fromId: string expected";
        if (message.toId != null && message.hasOwnProperty("toId"))
            if (!$util.isString(message.toId))
                return "toId: string expected";
        if (message.groupId != null && message.hasOwnProperty("groupId"))
            if (!$util.isString(message.groupId))
                return "groupId: string expected";
        if (message.body != null && message.hasOwnProperty("body"))
            if (!$util.isString(message.body))
                return "body: string expected";
        if (message.status != null && message.hasOwnProperty("status"))
            if (!$util.isInteger(message.status))
                return "status: integer expected";
        return null;
    };

    /**
     * Creates a MessageEntity message from a plain object. Also converts values to their respective internal types.
     * @function fromObject
     * @memberof MessageEntity
     * @static
     * @param {Object.<string,*>} object Plain object
     * @returns {Message} MessageEntity
     */
    Message.fromObject = function fromObject(object) {
        if (object instanceof $root.Message)
            return object;
        var message = new $root.Message();
        if (object.id != null)
            message.id = String(object.id);
        if (object.serverId != null)
            message.serverId = String(object.serverId);
        if (object.reqType != null)
            message.reqType = object.reqType | 0;
        if (object.msgTime != null)
            if ($util.Long)
                (message.msgTime = $util.Long.fromValue(object.msgTime)).unsigned = false;
            else if (typeof object.msgTime === "string")
                message.msgTime = parseInt(object.msgTime, 10);
            else if (typeof object.msgTime === "number")
                message.msgTime = object.msgTime;
            else if (typeof object.msgTime === "object")
                message.msgTime = new $util.LongBits(object.msgTime.low >>> 0, object.msgTime.high >>> 0).toNumber();
        if (object.syn != null)
            message.syn = object.syn | 0;
        if (object.ack != null)
            message.ack = String(object.ack);
        if (object.fromId != null)
            message.fromId = String(object.fromId);
        if (object.toId != null)
            message.toId = String(object.toId);
        if (object.groupId != null)
            message.groupId = String(object.groupId);
        if (object.body != null)
            message.body = String(object.body);
        if (object.status != null)
            message.status = object.status | 0;
        return message;
    };

    /**
     * Creates a plain object from a MessageEntity message. Also converts values to other types if specified.
     * @function toObject
     * @memberof MessageEntity
     * @static
     * @param {Message} message MessageEntity
     * @param {$protobuf.IConversionOptions} [options] Conversion options
     * @returns {Object.<string,*>} Plain object
     */
    Message.toObject = function toObject(message, options) {
        if (!options)
            options = {};
        var object = {};
        if (options.defaults) {
            object.id = "";
            object.serverId = "";
            object.reqType = 0;
            if ($util.Long) {
                var long = new $util.Long(0, 0, false);
                object.msgTime = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
            } else
                object.msgTime = options.longs === String ? "0" : 0;
            object.syn = 0;
            object.ack = "";
            object.fromId = "";
            object.toId = "";
            object.groupId = "";
            object.body = "";
            object.status = 0;
        }
        if (message.id != null && message.hasOwnProperty("id"))
            object.id = message.id;
        if (message.serverId != null && message.hasOwnProperty("serverId"))
            object.serverId = message.serverId;
        if (message.reqType != null && message.hasOwnProperty("reqType"))
            object.reqType = message.reqType;
        if (message.msgTime != null && message.hasOwnProperty("msgTime"))
            if (typeof message.msgTime === "number")
                object.msgTime = options.longs === String ? String(message.msgTime) : message.msgTime;
            else
                object.msgTime = options.longs === String ? $util.Long.prototype.toString.call(message.msgTime) : options.longs === Number ? new $util.LongBits(message.msgTime.low >>> 0, message.msgTime.high >>> 0).toNumber() : message.msgTime;
        if (message.syn != null && message.hasOwnProperty("syn"))
            object.syn = message.syn;
        if (message.ack != null && message.hasOwnProperty("ack"))
            object.ack = message.ack;
        if (message.fromId != null && message.hasOwnProperty("fromId"))
            object.fromId = message.fromId;
        if (message.toId != null && message.hasOwnProperty("toId"))
            object.toId = message.toId;
        if (message.groupId != null && message.hasOwnProperty("groupId"))
            object.groupId = message.groupId;
        if (message.body != null && message.hasOwnProperty("body"))
            object.body = message.body;
        if (message.status != null && message.hasOwnProperty("status"))
            object.status = message.status;
        return object;
    };

    /**
     * Converts this MessageEntity to JSON.
     * @function toJSON
     * @memberof MessageEntity
     * @instance
     * @returns {Object.<string,*>} JSON object
     */
    Message.prototype.toJSON = function toJSON() {
        return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
    };

    return Message;
})();

module.exports = $root;
