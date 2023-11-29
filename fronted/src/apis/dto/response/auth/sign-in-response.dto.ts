import ResponseDto from '..';

export default interface SignInResponseDto extends ResponseDto {
    token: string;
    expirationTime: number;
}