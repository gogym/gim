import * as $protobuf from "protobufjs";
import {Long} from "protobufjs";
/** Properties of a MessageEntity. */
export interface IMessage {

    /** MessageEntity id */
    id?: (string|null);

    /** MessageEntity serverId */
    serverId?: (string|null);

    /** MessageEntity reqType */
    reqType?: (number|null);

    /** MessageEntity msgTime */
    msgTime?: (number|Long|null);

    /** MessageEntity syn */
    syn?: (number|null);

    /** MessageEntity ack */
    ack?: (string|null);

    /** MessageEntity fromId */
    fromId?: (string|null);

    /** MessageEntity toId */
    toId?: (string|null);

    /** MessageEntity groupId */
    groupId?: (string|null);

    /** MessageEntity body */
    body?: (string|null);

    /** MessageEntity status */
    status?: (number|null);
}

/** Represents a MessageEntity. */
export class Message implements IMessage {

    /**
     * Constructs a new MessageEntity.
     * @param [properties] Properties to set
     */
    constructor(properties?: IMessage);

    /** MessageEntity id. */
    public id: string;

    /** MessageEntity serverId. */
    public serverId: string;

    /** MessageEntity reqType. */
    public reqType: number;

    /** MessageEntity msgTime. */
    public msgTime: (number|Long);

    /** MessageEntity syn. */
    public syn: number;

    /** MessageEntity ack. */
    public ack: string;

    /** MessageEntity fromId. */
    public fromId: string;

    /** MessageEntity toId. */
    public toId: string;

    /** MessageEntity groupId. */
    public groupId: string;

    /** MessageEntity body. */
    public body: string;

    /** MessageEntity status. */
    public status: number;

    /**
     * Creates a new MessageEntity instance using the specified properties.
     * @param [properties] Properties to set
     * @returns Message instance
     */
    public static create(properties?: IMessage): Message;

    /**
     * Encodes the specified MessageEntity message. Does not implicitly {@link Message.verify|verify} messages.
     * @param message MessageEntity message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encode(message: IMessage, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Encodes the specified MessageEntity message, length delimited. Does not implicitly {@link Message.verify|verify} messages.
     * @param message MessageEntity message or plain object to encode
     * @param [writer] Writer to encode to
     * @returns Writer
     */
    public static encodeDelimited(message: IMessage, writer?: $protobuf.Writer): $protobuf.Writer;

    /**
     * Decodes a MessageEntity message from the specified reader or buffer.
     * @param reader Reader or buffer to decode from
     * @param [length] MessageEntity length if known beforehand
     * @returns Message
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): Message;

    /**
     * Decodes a MessageEntity message from the specified reader or buffer, length delimited.
     * @param reader Reader or buffer to decode from
     * @returns Message
     * @throws {Error} If the payload is not a reader or valid buffer
     * @throws {$protobuf.util.ProtocolError} If required fields are missing
     */
    public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): Message;

    /**
     * Verifies a MessageEntity message.
     * @param message Plain object to verify
     * @returns `null` if valid, otherwise the reason why it is not
     */
    public static verify(message: { [k: string]: any }): (string|null);

    /**
     * Creates a MessageEntity message from a plain object. Also converts values to their respective internal types.
     * @param object Plain object
     * @returns Message
     */
    public static fromObject(object: { [k: string]: any }): Message;

    /**
     * Creates a plain object from a MessageEntity message. Also converts values to other types if specified.
     * @param message MessageEntity
     * @param [options] Conversion options
     * @returns Plain object
     */
    public static toObject(message: Message, options?: $protobuf.IConversionOptions): { [k: string]: any };

    /**
     * Converts this MessageEntity to JSON.
     * @returns JSON object
     */
    public toJSON(): { [k: string]: any };
}
