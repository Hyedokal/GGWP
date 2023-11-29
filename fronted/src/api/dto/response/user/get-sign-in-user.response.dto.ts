import { LoginUser } from 'types';
import ResponseDto from '..';

export default interface GetSignInUserResponseDto extends ResponseDto, LoginUser {

}