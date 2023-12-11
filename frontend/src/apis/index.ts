import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './dto/request/auth';
import { SignInResponseDto, SignUpResponseDto } from './dto/response/auth';
import ResponseDto from './dto/response';
import {GetSignInUserResponseDto, GetUserResponseDto, PatchLolNickNameResponseDto} from "./dto/response/user";
import PatchLolNickNameRequestDto from "./dto/request/user/patch-lol-nickName-request.dto";
import SquadRequestDto from "./dto/request/squad/SquadRequestDto";
import SquadResponseDto from "./dto/response/squad/SquadResponseDto";


// 포트번호 노출시키지 마세요, .env 파일에 저장해서 환경변수로 불러오세요
const DOMAIN = 'http://localhost:8000'; // description: 내 URL // 나중엔 gateway주소 //

const API_DOMAIN = `${DOMAIN}/member-service/v1`; // description: API Domain 주소 //


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



// description: get user API end point //
const GET_USER_URL = () => `${API_DOMAIN}/member/userInfo`;

// // description: get user request //
export const getUserRequest = async (token: string) => {
    const result = await axios.get(GET_USER_URL(), authorization(token))
        .then(response => {
            const responseBody: GetUserResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });

    return result;
};




// description: patch user email API end point //

const PATCH_LOLNAME_URL = () => `${API_DOMAIN}/member/lolNickname`;

export const patchLolNicknameRequest = async (requestBody: PatchLolNickNameRequestDto, token:string): Promise<string> => {
    try {
        const response = await axios.patch(PATCH_LOLNAME_URL(), requestBody, authorization(token))
        const responseBody: PatchLolNickNameResponseDto = response.data;
        const { code } = responseBody;
        return code;

    }catch(error){
        if (axios.isAxiosError(error) && error.response) {
            const responseBody:ResponseDto= error.response.data;
            const {code}= responseBody;
            return code;
        }else{
        throw error;
    }
    }
};


// Define the endpoint path
const SQUAD_ENDPOINT = '/v1/squads';

//글쓰기 요청
export const sendSquadRequest = async (requestBody: SquadRequestDto): Promise<SquadResponseDto | string> => {
    try {
        const response = await axios.post(DOMAIN + SQUAD_ENDPOINT, requestBody);
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.response) {
            const responseBody: ResponseDto = error.response.data;
            return responseBody.message;
        } else {
            throw error;
        }
    }
};