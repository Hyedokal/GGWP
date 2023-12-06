import { Route, Routes, useLocation } from 'react-router-dom';
import './App.css';
import {AUTH_PATH, MAIN_PATH, USER_PATH} from 'constant';

import Main from 'views/Main';
import Authentication from 'views/Authentication';
import  User  from 'views/User';
import Container from 'layouts/Container';
import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useUserStore } from 'stores';

import {getSignInUserRequest, getUserRequest} from 'apis';
import {GetSignInUserResponseDto, GetUserResponseDto} from 'apis/dto/response/user';
import ResponseDto from 'apis/dto/response';
import UserInfoStore from "./stores/userInfo.store";
import Test from "./views/Test";

function App() {

  const { pathname } = useLocation();   //          state: 현재 페이지 url 상태          //
  const { user, setUser } = useUserStore();   //          state: 로그인 유저 상태          //
  const [cookies, setCookie] = useCookies();  //          state: cookie 상태          //
  const { userInfo, setUserInfo } = UserInfoStore();

    //          function: get sign in user response 처리 함수 //
  const getSignInUserResponse = (responseBody: GetSignInUserResponseDto | ResponseDto) => {
    const { code } = responseBody;
    if (code !== 'SU') {
      setCookie('accessToken', '', { expires: new Date(), path: MAIN_PATH });
      setUser(null);
      return;
    }

    setUser({ ...responseBody as GetSignInUserResponseDto });
  }

  // function: getUserRequest 처리 함수
    const getUserResponse = (responseBody: GetUserResponseDto | ResponseDto) => {
    const {code} = responseBody;
    if(code !== 'SU'){
      setUserInfo(null);
      return;
    }
      setUserInfo({...responseBody as GetUserResponseDto});
      }


  useEffect(() => {    //          effect: 현재 path가 변경될 때마다 실행될 함수          //
    const accessToken = cookies.accessToken;
    if (!accessToken) {
      setUser(null);
      return;
    }

    getSignInUserRequest(accessToken).then(getSignInUserResponse);
    getUserRequest(accessToken).then(getUserResponse);
  }, [pathname]);



  return (
      <Routes>
        <Route element={<Container /> }>
          <Route path={MAIN_PATH} element={<Main />} />
          <Route path={AUTH_PATH} element={<Authentication/>}/>
          <Route path={USER_PATH} element={<User />} />
          <Route path='/test' element={<Test/>} />
          <Route path='*' element={<h1>404 Not Found</h1>} />
        </Route>
      </Routes>
  );
}

export default App;


