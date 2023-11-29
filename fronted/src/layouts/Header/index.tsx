import { useLocation, useNavigate, useParams } from 'react-router-dom';
import './style.css';
import { useCookies } from 'react-cookie';
import {useUserStore} from "../../stores";
import {AUTH_PATH, MAIN_PATH, USER_PATH} from "../../constant";
import {LoginUser} from "../../types";
import {useEffect} from "react";


// 헤더 컴포넌트
export default function Header() {
    const { pathname } = useLocation();    //          state: path name 상태          //
    const { user, setUser } = useUserStore();     //          state: 로그인 유저 상태          //
    const [cookies, setCookies] = useCookies();   //          state: cookie 상태          //
    const isAuthPage = pathname === AUTH_PATH;   //          variable: 인증 페이지 논리 변수          //
    const isMainPage = pathname === MAIN_PATH;   //          variable: 메인 페이지 논리 변수          //
    const navigator = useNavigate();     //          function: 네비게이트 함수          //


    const onLogoClickHanlder = () => {       //          event handler: 로고 클릭 이벤트 처리          //

        navigator(MAIN_PATH);
    }

    const LoginMyPageButton = () => {   //          component: 로그인 상태에 따라 로그인 혹은 마이페이지 버튼 컴포넌트          //


        const onMyPageButtonClickHandler = () => {         //          event handler: 마이페이지 버튼 클릭 이벤트 처리         //

            if (!user) return;
            navigator(USER_PATH(user.email));
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


    const UserPageButtons = () => { //          component: 유저 페이지 버튼 컴포넌트          //

        const { searchEmail } = useParams();         //          state: path variable의 email 상태          //



        const isMyPage = user && user.email === searchEmail;   //          variable: 마이페이지 여부 논리 변수          //

        const onLogoutButtonClickHandler = () => {         //          event handler: 로그아웃 버튼 클릭 이벤트 처리          //
            setCookies('accessToken', '', { path: '/', expires: new Date() });
            setUser(null);
        }

        if (isMyPage)          //          render: 본인 페이지 일 때 버튼 컴포넌트 렌더링          //
            return (<div className='logout-button' onClick={onLogoutButtonClickHandler}>로그아웃</div>);
        return (<LoginMyPageButton />);          //          render: 타인 페이지 일 때 버튼 컴포넌트 렌더링          //

    }

    useEffect(() => {    //          effect: 마운트시에만 실행될 함수          //
        if (cookies.email) {
            const user: LoginUser = { email: cookies.email, nickname: '주코야키', profileImage: null };
            setUser(user);
        }
    }, []);



    return ( // 렌더링 헤더 레이아웃

        <div id='header'>
            <div className='header-container'>
                <div className='header-left-box'onClick={onLogoClickHanlder} >
                    <div className='header-logo-icon-box' >
                        <div className='logo-dark-icon'></div>
                    </div>
                    <div className='header-logo-text'>{'GGWP'}</div>
                </div>
                <div>매칭 해요</div>
                <div>검색 해요</div>
                <div className='header-right-box'>
                    { isMainPage && (<LoginMyPageButton />) }
                </div>
            </div>
        </div>
    )

}