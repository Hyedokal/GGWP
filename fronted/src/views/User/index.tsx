    import { useUserStore } from 'stores';
    import {ChangeEvent, useState} from "react";
    import {useNavigate} from "react-router-dom";
    import {AUTH_PATH} from "../../constant";
    import {useCookies} from "react-cookie";
    import PatchEmailRequestDto from "../../apis/dto/request/user/patch-email-request.dto";
    import {patchUserEmailRequest} from "../../apis";


    //          component: 유저 페이지          //
    export default function User() {


        //          state: 로그인 유저 정보 상태           //
        const { user, setUser } = useUserStore();
        //          state: 본인 여부 상태           //
        const [isMyPage, setMyPage] = useState<boolean>(false);

        //          function: 네비게이트 함수          //
        const navigator = useNavigate();

        const UserInfo = () => {

            //          state: cookie 상태           //
            const [cookies, setCookie] = useCookies();


            //          state: 기존 이메일 상태           //
            const [existingEmail, setExistingEmail] = useState<string>('');

            //이메일 상태
            const [email, setEmail] = useState<string>('');

            //이메일 변경 상태
            const [showChangeEmail, setShowChangeEmail] = useState<boolean>(false);


            // 이메일 변경 response
            const PatchEmailResponse = (code: string) => {
                if (code === 'AF' || code === 'NU') {
                    alert('로그인이 필요합니다.');
                    navigator(AUTH_PATH);
                    return;
                }
                if (code === 'VF') alert('빈 값일 수 없습니다.');
                if (code === 'DN') alert('중복되는 닉네임입니다.');
                if (code === 'DBE') alert('데이터베이스 오류입니다.');

                if (code !== 'SU') {
                    alert('오류가 발생했습니다.'); // 이메일이 중복될확률이있음. 나중에 이메일중복되었다고 추가
                    return;
                }
                setShowChangeEmail(false);

            }


            //          event handler: 이메일 변경 버튼 클릭 이벤트 처리          //
            const onChangeEmailButtonClickHandler = () => {
                if (!showChangeEmail) {
                    setShowChangeEmail(true);
                    return;
                }
                const isEqual = email === existingEmail;
                if (isEqual) {
                    setShowChangeEmail(false);
                    return;
                }

                const accessToken = cookies.accessToken;
                if (!accessToken) return;

                const requestBody: PatchEmailRequestDto = {email};
                // 이메일 변경 요청
                patchUserEmailRequest(requestBody, accessToken).then(PatchEmailResponse);
            }


            //          event handler: 닉네임 변경 이벤트 처리          //
            const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
                const email = event.target.value;
                setEmail(email);
            };

            return (
                <div id='user-info-wrapper'>

                    <div className='user-info-container'>
                        <div className='user-info-title'>이메일</div>
                        <div className='user-info-content'>
                            {showChangeEmail ?
                                <input type='text' value={email} onChange={onEmailChangeHandler}/> :
                                <>{user?.email}</>
                            }
                        </div>
                        <div className='user-info-button' onClick={onChangeEmailButtonClickHandler}>
                            {showChangeEmail ? '변경' : '변경하기'}
                        </div>
                      </div>
                </div>
            );
        };
        return(
            <>
            <UserInfo/>
            </>
        )
    }
