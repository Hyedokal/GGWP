import { useLocation, useNavigate, useParams } from 'react-router-dom';
import './style.css';
import { useCookies } from 'react-cookie';
import {useUserStore} from "../../stores";
import {AUTH_PATH, MAIN_PATH, MATCH_PATH, USER_PATH} from "../../constant";
import {LoginUser} from "../../types";
import {useEffect} from "react";


// 헤더 컴포넌트
export default function Header() {
    const { pathname } = useLocation();    //          state: path name 상태          //
    const { user, setUser } = useUserStore();     //          state: 로그인 유저 상태          //
    const [cookies, setCookies] = useCookies();   //          state: cookie 상태          //
    const isAuthPage = pathname === AUTH_PATH;   //          variable: 인증 페이지 논리 변수          //
    const isMainPage = pathname === MAIN_PATH;   //          variable: 메인 페이지 논리 변수          //
    const isUserPage = pathname === USER_PATH;   //          variable: 유저 페이지 논리 변수          //

    const isMatchPage = pathname=== MATCH_PATH;   //          variable: 매칭 페이지 논리 변수          //
    //모든 페이지 논리변수
    const allPage = isAuthPage || isMainPage || isUserPage || isMatchPage;


    const navigator = useNavigate();     //          function: 네비게이트 함수          //

    const onMatchingClickHandler = () => {  //          event handler: 매칭 클릭 이벤트 처리          //
        if (!cookies.accessToken) {
            alert('Please register and log in');
        } else {
            navigator(MATCH_PATH);
        }
    }
    const onLogoClickHanlder = () => {       //          event handler: 로고 클릭 이벤트 처리          //

        navigator(MAIN_PATH);
    }

    const LoginMyPageButton = () => {   //          component: 로그인 상태에 따라 로그인 혹은 마이페이지 버튼 컴포넌트          //


        const onMyPageButtonClickHandler = () => {         //          event handler: 마이페이지 버튼 클릭 이벤트 처리         //

            if (!user) return;
            navigator(USER_PATH);
        }
        const onLoginButtonClickHandler = () => {         //          event handler: 로그인 버튼 클릭 이벤트 처리         //
            navigator(AUTH_PATH);
        }

        if (cookies.accessToken)
            return (
                <div className='mypage-button' onClick={onMyPageButtonClickHandler}>마이페이지</div>   //          render: 마이페이지 버튼 컴포넌트 렌더링 (로그인 상태일 때)         //
            );
        return (
            <div className='login-button' onClick={onLoginButtonClickHandler}>로그인</div>         //          render: 로그인 버튼 컴포넌트 렌더링 (로그인 상태가 아닐 때)         //

        );
    };

        //컴포넌트 로그인상태 일시  로그아웃 버튼 컴포넌트
    const LogoutButton = () => {   //          component: 로그인 상태에 따라 로그인 혹은 마이페이지 버튼 컴포넌트          //

            const onLogoutButtonClickHandler = () => {         //          event handler: 로그아웃 버튼 클릭 이벤트 처리         //
                setCookies('accessToken', '', { path: '/', expires: new Date() });
                setUser(null);
                alert('You have been logged out.'); // Show an alert message
                navigator(MAIN_PATH); // Navigate to the main page

            }
        if (!cookies.accessToken) {
            return null;
        }
            return (
                <div className='logout-button' onClick={onLogoutButtonClickHandler}>로그아웃</div>         //          render: 로그아웃 버튼 컴포넌트 렌더링 (로그인 상태일 때)         //
            );
    }



    return ( // 렌더링 헤더 레이아웃

        <div id='header'>
            <div className='header-container'>
                <div className='header-left-box'onClick={onLogoClickHanlder} >
                    <div className='header-logo-icon-box' >
                        <div className='logo-dark-icon'></div>
                    </div>
                    <div className='header-logo-text'>{'GGWP'}</div>
                </div>
               <div className="match-button" onClick={onMatchingClickHandler}>
                <div >매칭해요</div>
               </div>
                <div>검색 해요</div>
                <div className='header-right-box'>
                    { isMainPage && (<LoginMyPageButton />) }
                    { allPage && (<LogoutButton />) }
                </div>
            </div>
        </div>
    )

}