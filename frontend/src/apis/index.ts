import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './dto/request/auth';
import { SignInResponseDto, SignUpResponseDto } from './dto/response/auth';
import ResponseDto from './dto/response';
import {GetSignInUserResponseDto, GetUserResponseDto, PatchLolNickNameResponseDto} from "./dto/response/user";
import PatchLolNickNameRequestDto from "./dto/request/user/patch-lol-nickName-request.dto";
import SquadRequestDto from "./dto/request/squad/SquadRequestDto";
import SquadResponseDto from "./dto/response/squad/SquadResponseDto";
import exp from "constants";


const DOMAIN = 'http://localhost:8000';

const MEMBER_API_DOMAIN = `${DOMAIN}/member-service/v1`; // description: API Domain 주소 //

const authorization = (token: string) => {// description: Authorizaition Header //
    return { headers: { Authorization: `Bearer ${token}` } };
};



const SIGN_UP_URL = () => `${MEMBER_API_DOMAIN}/auth/sign-up`;

const SIGN_IN_URL = () => `${MEMBER_API_DOMAIN}/auth/sign-in`;



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






const GET_SIGN_IN_USER_URL = () => `${MEMBER_API_DOMAIN}/member`;  // description: get sign in user API end point //



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
const GET_USER_URL = () => `${MEMBER_API_DOMAIN}/member/userInfo`;

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

const PATCH_LOLNAME_URL = () => `${MEMBER_API_DOMAIN}/member/lolNickname`;

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










//-------------------------------------게시판 api-------------------------------------//

//공지사항 작성
export const postNewAnnouncement = async (title: string, content: string) => {
    try {
        const response = await axios.post('http://localhost:8000/v1/announces', { title, content });
        return response.data;
    } catch (error: unknown) {
        // Type guard to ensure error is an instance of Error
        if (error instanceof Error) {
            throw new Error('Error creating new announcement: ' + error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }
};

//공지사항 삭제
export const deleteAnnouncementApi = async (announceId: number) => {
    try {
        await axios.delete(`http://localhost:8000/v1/announces/${announceId}`);
    } catch (error) {
        throw new Error(`Error deleting announcement: ${error instanceof Error ? error.message : "Unknown error"}`);
    }
};

//공지사항 수정
export const updateAnnouncementApi = async (announcementId: number | null, title: string, content: string) => {
    if (announcementId === null) {
        throw new Error("No announcement ID provided for update.");
    }
    try {
        await axios.put(`http://localhost:8000/v1/announces/${announcementId}`, { title, content });
    } catch (error) {
        throw new Error(`Error updating announcement: ${error instanceof Error ? error.message : "Unknown error"}`);
    }
};
//공지사항 조회
export const fetchAnnouncementsApi = async (page: number) => {
    try {
        const response = await axios.post('http://localhost:8000/v1/announces/search', { page: page });
        return response.data;
    } catch (error) {
        throw new Error(`Error fetching announcements: ${error instanceof Error ? error.message : "Unknown error"}`);
    }
};



// squad 서비스 api 정리
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

export const postCommentApi = async (sId: number, wontPos: string, qType: string, useMic: boolean, summonerName: string, tagLine: string, memo: string) => {
    try {
        const response = await axios.post('http://localhost:8000/v1/comments', {
            sId,
            wontPos,
            qType,
            useMic,
            summonerName,
            tagLine,
            memo
        });
        return response.data;
    } catch (error) {
        throw new Error(`Error posting comment: ${error instanceof Error ? error.message : "Unknown error"}`);
    }
};
