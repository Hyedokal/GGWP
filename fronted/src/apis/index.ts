import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './dto/request/auth';
import { SignInResponseDto, SignUpResponseDto } from './dto/response/auth';
import ResponseDto from './dto/response';
import {GetSignInUserResponseDto, GetUserResponseDto} from "./dto/response/user";




const DOMAIN = 'http://localhost:8080'; // description: 내 URL // 나중엔 gateway주소 //

const API_DOMAIN = `${DOMAIN}/v1`; // description: API Domain 주소 //


const authorization = (token: string) => {// description: Authorizaition Header //
    return { headers: { Authorization: `Bearer ${token}` } };
};



const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`; // description: sign up API end point //

const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;// description: sigin in API end point //



export const signUpRequest  = async (requestBody: SignUpRequestDto) => { // description: sign up request //

    const result = await axios.post(SIGN_UP_URL(), requestBody)
        .then(response => {
            const responseBody: SignUpResponseDto = response.data;
            const { code } = responseBody;
            return code;
        })
        .catch(error => {
            const responseBody: ResponseDto = error.response.data;
            const { code } = responseBody;
            return code;
        });
    return result;
};


export const signInRequest = async (requestBody: SignInRequestDto) => { // description: sign in request //

    const result = await axios.post(SIGN_IN_URL(), requestBody)
        .then(response => {
            const responseBody: SignInResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
    return result;
};






const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/member`;  // description: get sign in user API end point //



export const getSignInUserRequest = async (token: string) => {  // description: get sign in user request //

    const result = await axios.get(GET_SIGN_IN_USER_URL(), authorization(token))
        .then(response => {
            const responseBody: GetSignInUserResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
    return result;
};










