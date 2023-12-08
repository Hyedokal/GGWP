import {ResponseCode, ResponseMessage} from "./ResponseType";

export default interface ResponseDto {
    code: ResponseCode;
    message: ResponseMessage;
}