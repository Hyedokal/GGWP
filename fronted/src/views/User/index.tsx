import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import './style.css';
import { useNavigate, useParams } from 'react-router-dom';
import { useUserStore } from 'stores';
import { AUTH_PATH, MAIN_PATH, USER_PATH } from 'constant';
import {  getSignInUserRequest, getUserRequest } from 'apis';
import { GetSignInUserResponseDto, GetUserResponseDto } from 'apis/dto/response/user';
import ResponseDto from 'apis/dto/response';
import { useCookies } from 'react-cookie';

//          component: 유저 페이지          //
export default function User() {

  //          state: 조회하는 유저 이메일 path variable 상태           //
  const { searchEmail } = useParams();
  //          state: 로그인 유저 정보 상태           //
  const { user, setUser } = useUserStore();
  //          state: 본인 여부 상태           //
  const [isMyPage, setMyPage] = useState<boolean>(false);

  //          function: 네비게이트 함수          //
  const navigator = useNavigate();

  //          component: 유저 정보 컴포넌트          //
  const UserInfo = () => {

    //          state: cookie 상태           //
    const [cookies, setCookie] = useCookies();
    //          state: 이메일 상태           //
    const [email, setEmail] = useState<string>('');
    //          state: 프로필 이미지 상태           //
    const [profileImage, setProfileImage] = useState<string | null>('');
    //          state: 기존 닉네임 상태           //
    const [existingNickname, setExistingNickname] = useState<string>('');
    //          state: 닉네임 상태           //
    const [nickname, setNickname] = useState<string>('');
    //          state: 닉네임 변경 상태           //
    const [showChangeNickname, setShowChangeNickname] = useState<boolean>(false);

    //          function: get user response 처리 함수          //
    const getUserResponse = (responseBody: GetUserResponseDto | ResponseDto) => {
      const { code } = responseBody;
      if (code === 'NU') alert('존재하지 않는 유저입니다.');
      if (code === 'DBE') alert('데이터베이스 오류입니다.');
      if (code !== 'SU') {
        navigator(MAIN_PATH);
        return;
      }

      const { email, nickname } = responseBody as GetUserResponseDto;
      setEmail(email);
      setNickname(nickname);
      setExistingNickname(nickname);
      setProfileImage(profileImage);
    };


    //          function: get sign in user response 처리 함수 //
    const getSignInUserResponse = (responseBody: GetSignInUserResponseDto | ResponseDto) => {
      const { code } = responseBody;
      if (code !== 'SU') {
        setCookie('accessToken', '', { expires: new Date(), path: MAIN_PATH });
        setUser(null);
        return;
      }

      setUser({ ...responseBody as GetSignInUserResponseDto });
    };


    //          effect: 조회하는 유저의 이메일이 변경될 때 마다 실행할 함수          //
    useEffect(() => {
      if (!searchEmail) {
        navigator(MAIN_PATH);
        return;
      }
      getUserRequest(searchEmail).then(getUserResponse);
    }, [searchEmail]);

    //          render: 유저 정보 컴포넌트 렌더링          //
    return (
        <div id='user-info-wrapper'>
          <div className='user-info-container'>
            <div className={isMyPage ? 'user-info-profile-image-box-mypage' : 'user-info-profile-image-box'} >
              <input  type='file' accept='image/*' style={{ display: 'none' }}  />
              {profileImage === null ? (
                  <div className='user-info-profile-default-image'>
                    <div className='user-info-profile-icon-box'>
                      <div className='image-box-white-icon'></div>
                    </div>
                  </div>
              ) : (
                  <div className='user-info-profile-image' style={{ backgroundImage: `url(${profileImage})` }}></div>
              )}
            </div>
            <div className='user-info-meta-box'>
              <div className='user-info-nickname-box'>
                {showChangeNickname ? (
                    <input className='user-info-nickname-input' type='text' size={nickname.length + 1} value={nickname}  />
                ) : (
                    <div className='user-info-nickname'>{nickname}</div>
                )}
                {isMyPage && (
                    <div className='icon-button' >
                      <div className='edit-light-icon'></div>
                    </div>
                )}
              </div>
              <div className='user-info-email'>{email}</div>
            </div>
          </div>
        </div>
    );

  };


  //          effect: 조회하는 유저의 이메일이 변경될 때 마다 실행할 함수 //
  useEffect(() => {
    const isMyPage = searchEmail === user?.email;
    setMyPage(isMyPage);
  } , [searchEmail, user]);

  //          render: 유저 페이지 렌더링          //
  return (
      <>
        <UserInfo />
      </>
  )
}
