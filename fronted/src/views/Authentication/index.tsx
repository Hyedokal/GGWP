
import { KeyboardEvent, useRef, useState } from 'react';
import './style.css';
import InputBox from 'components/InputBox';
import { useCookies } from 'react-cookie';
import { useUserStore } from 'stores';
import { useNavigate } from 'react-router-dom';
import { MAIN_PATH } from 'constant';
import { signInRequest, signUpRequest } from 'apis';
import { SignInRequestDto, SignUpRequestDto } from 'apis/dto/request/auth';
import { SignInResponseDto } from 'apis/dto/response/auth';
import ResponseDto from 'apis/dto/response';

export default function Authentication() { // component 인증화면 컴포넌트
    const {user, setUser} = useUserStore();    //          state: 로그인 유저 전역 상태          //
    const [cookies, setCookie] = useCookies();    //          state: 쿠키 상태          //

    const [view, setView] = useState<'sign-in' | 'sign-up'>('sign-in');  //          state: 화면 상태          //


    const navigator = useNavigate();   //          function: 네비게이트 함수          //


    const SignInCard = () => {    //          component: sign in 카드 컴포넌트          //


        const passwordRef = useRef<HTMLInputElement | null>(null);        //          state: 비밀번호 입력 요소 참조 상태          //


        const [email, setEmail] = useState<string>('');        //          state: 입력한 이메일 상태          //

        const [password, setPassword] = useState<string>('');        //          state: 입력한 비밀번호 상태          //
        const [passwordType, setPasswordType] = useState<'text' | 'password'>('password');     //          state: 비밀번호 인풋 타입 상태          //

        const [passwordIcon, setPasswordIcon] = useState<'eye-off-icon' | 'eye-on-icon'>('eye-off-icon');    //          state: 비밀번호 인풋 버튼 아이콘 상태          //


        const [error, setError] = useState<boolean>(false);        //          state: 로그인 에러 상태          //


        const signInResponse = (responseBody: SignInResponseDto | ResponseDto) => {         //          function: sign in response 처리 함수          //

            const {code} = responseBody;
            if (code === 'VF') alert('모두 입력해주세요.');
            if (code === 'SF') setError(true);
            if (code === 'DBE') alert('데이터베이스 오류입니다.');
            if (code !== 'SU') return;

            const {token, expirationTime} = responseBody as SignInResponseDto;

            const now = new Date().getTime();
            const expires = new Date(now + expirationTime * 1000);

            setCookie('accessToken', token, {expires, path: MAIN_PATH});
            navigator(MAIN_PATH);

        }


        const onEmailKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => { //          event handler: 이메일 인풋 key down 이벤트 처리          //
            if (event.key !== 'Enter') return;
            if (!passwordRef.current) return;
            passwordRef.current.focus();
        }

        const onPasswordKeyDownHanlder = (event: KeyboardEvent<HTMLInputElement>) => {        //          event handler: 비밀번호 인풋 key down 이벤트 처리          //

            if (event.key !== 'Enter') return;
            onSignInButtonClickHandler();
        }
        const onPasswordIconClickHandler = () => {     //          event handler: 비밀번호 인풋 버튼 클릭 이벤트 처리          //

            if (passwordType === 'text') {
                setPasswordType('password');
                setPasswordIcon('eye-off-icon');
            }
            if (passwordType === 'password') {
                setPasswordType('text');
                setPasswordIcon('eye-on-icon');
            }
        }


        //          event handler: 로그인 버튼 클릭 이벤트 처리          //
        const onSignInButtonClickHandler = () => {
            const requestBody: SignInRequestDto = {email, password};  // 이게 dto 다
            signInRequest(requestBody).then(signInResponse);
        }

        //          event handler: 회원가입 링크 클릭 이벤트 처리          //
        const onSignUpLinkClickHandler = () => {
            setView('sign-up');
        }

        //          render: sign in 카드 컴포넌트 렌더링         //
        return (
            <div className='auth-card'>
                <div className='auth-card-top'>
                    <div className='auth-card-title-box'>
                        <div className='auth-card-title'>{'로그인'}</div>
                    </div>
                    <InputBox label='이메일 주소' type='text' placeholder='이메일 주소를 입력해주세요.' error={error} value={email}
                              setValue={setEmail} onKeyDown={onEmailKeyDownHandler}/>
                    <InputBox ref={passwordRef} label='비밀번호' type={passwordType} placeholder='비밀번호를 입력해주세요.' error={error} value={password} setValue={setPassword} icon={passwordIcon} onKeyDown={onPasswordKeyDownHanlder} onButtonClick={onPasswordIconClickHandler} />

                </div>
                <div className='auth-card-bottom'>
                    {error && (
                        <div className='auth-sign-in-error-box'>
                            <div className='au  th-sign-in-error-message'>
                                {'이메일 주소 또는 비밀번호를 잘못 입력했습니다.\n입력하신 내용을 다시 확인해주세요.'}
                            </div>
                        </div>
                    )}
                    <div className='auth-button' onClick={onSignInButtonClickHandler}>{'로그인'}</div>
                    <div className='auth-description-box'>
                        <div className='auth-description'>{'신규 사용자이신가요? '}<span className='description-emphasis'
                                                                                onClick={onSignUpLinkClickHandler}>{'회원가입'}</span>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

        const SignUpCard = () => {           //          component: sign up 카드 컴포넌트          //


            const [page, setPage] = useState<1 | 2>(1);              //          state: 페이지 번호 상태          //
            const [email, setEmail] = useState<string>('');            //          state: 이메일 상태          //
            const [emailError, setEmailError] = useState<boolean>(false);             //          state: 이메일 에러 상태          //
            const [emailErrorMessage, setEmailErrorMessage] = useState<string>('');//          state: 이메일 에러 메세지 상태          //



            const [password, setPassword] = useState<string>('');            //          state: 비밀번호 상태          //
            //          state: 비밀번호 타입 상태          //
            const [passwordType, setPasswordType] = useState<'text' | 'password'>('password');
            //          state: 비밀번호 아이콘 상태          //
            const [passwordIcon, setPasswordIcon] = useState<'eye-on-icon' | 'eye-off-icon'>('eye-off-icon');

            const [passwordError, setPasswordError] = useState<boolean>(false);            //          state: 비밀번호 에러 상태          //
            const [passwordErrorMessage, setPasswordErrorMessage] = useState<string>('');            //          state: 비밀번호 에러 메세지 상태          //

            const [passwordCheck, setPasswordCheck] = useState<string>('');            //          state: 비밀번호 확인 상태          //

            //          state: 비밀번호 확인 타입 상태          //
            const [passwordCheckType, setPasswordCheckType] = useState<'text' | 'password'>('password');
            //          state: 비밀번호 확인 아이콘 상태          //
            const [passwordCheckIcon, setPasswordCheckIcon] = useState<'eye-on-icon' | 'eye-off-icon'>('eye-off-icon');
            const [passwordCheckError, setPasswordCheckError] = useState<boolean>(false);            //          state: 비밀번호 확인 에러 상태          //
            const [passwordCheckErrorMessage, setPasswordCheckErrorMessage] = useState<string>('');            //          state: 비밀번호 확인 에러 메세지 상태          //


            const [lolNickname, setLolNickname] = useState<string>('');            //          state: 롤 닉네임 상태          //
            const [lolNicknameError, setLolNicknameError] = useState<boolean>(false);            //          state:롤 닉네임 에러 상태          //

            const [lolNicknameErrorMessage, setLolNicknameErrorMessage] = useState<string>('');             //          state: 롤 닉네임 에러 메세지 상태          //


            const [tag, setTag] = useState<string>('');            //          tag: 주소 상태          //
            const [tagError, setTagError] = useState<boolean>(false);            //          state: 태그 에러 상태          //
            const [tagErrorMessage, setTagErrorMessage] = useState<string>('');            //          state: 태그 에러 메세지 상태          //


            const [consent, setConsent] = useState<boolean>(false);            //          state: 개인정보동의 상태          //

            const [consentError, setConsentError] = useState<boolean>(false);            //          state: 개인정보동의 에러 상태          //


            const signUpResponse = (code: string) => {             //          function: sign up response 처리 함수          //

                if (code === 'VF') alert('모두 입력하세요.');
                if (code === 'DE') {
                    setEmailError(true);
                    setEmailErrorMessage('중복되는 이메일 주소 입니다.');
                    setPage(1);
                }

                if (code === 'DBE') alert('데이터베이스 오류입니다.');
                if (code !== 'SU') return;


                setEmail('');
                setPassword('');
                setLolNickname('');
                setTag('');
                setConsent(false);
                setPage(1);
                setView('sign-in');

            }

            //          event handler: 비밀번호 아이콘 클릭 이벤트 처리          //
            //          event handler: 로그인 링크 클릭 이벤트 처리          //

            const onSignInLinkClickHandler = () => {
                setView('sign-in');
            };
            const onPasswordIconClickHandler = () => {
                if (passwordType === 'password') {
                    setPasswordType('text');
                    setPasswordIcon('eye-on-icon');
                }
                if (passwordType === 'text') {
                    setPasswordType('password');
                    setPasswordIcon('eye-off-icon');
                }
            }
            //          event handler: 비밀번호 확인 아이콘 클릭 이벤트 처리          //
            const onPasswordCheckIconClickHandler = () => {
                if (passwordCheckType === 'text') {
                    setPasswordCheckType('password');
                    setPasswordCheckIcon('eye-off-icon');
                }
                if (passwordCheckType === 'password') {
                    setPasswordCheckType('text');
                    setPasswordCheckIcon('eye-on-icon');
                }
            }

            //          event handler: 개인정보동의 체크 이벤트 처리          //
            const onConsentCheckHandler = () => {
                setConsent(!consent);
            }


            //          event handler: 다음 단계 버튼 클릭 이벤트 처리          //
            const onNextStepButtonClickHandler = () => {

                setEmailError(false);
                setEmailErrorMessage('');
                setPasswordError(false);
                setPasswordErrorMessage('');
                setPasswordCheckError(false);
                setPasswordCheckErrorMessage('');

                // description: 이메일 패턴 확인 //
                const emailPattern = /^[a-zA-Z0-9]*@([-.]?[a-zA-Z0-9])*\.[a-zA-Z]{2,4}$/;
                const checkedEmail = !emailPattern.test(email);
                if (checkedEmail) {
                    setEmailError(true);
                    setEmailErrorMessage('이메일주소 포맷이 맞지않습니다.');
                }
                // description: 비밀번호 길이 확인 //
                const checkedPassword = password.trim().length < 8;
                if (checkedPassword) {
                    setPasswordError(true);
                    setPasswordErrorMessage('비밀번호는 8자 이상 입력해주세요.');
                }
                // description: 비밀번호 일치 여부 확인 //
                const checkedPasswordCheck = password !== passwordCheck;
                if (checkedPasswordCheck) {
                    setPasswordCheckError(true);
                    setPasswordCheckErrorMessage('비밀번호가 일치하지않습니다.');
                }

                if (checkedEmail || checkedPassword || checkedPasswordCheck) return;

                setPage(2);
            }


            //          event handler: 회원가입 버튼 클릭 이벤트 처리          //
            const onSignUpButtonClickHandler = () => {
                setLolNicknameError(false);
                setTagError(false);
                setConsentError(false);

                // description: 닉네임 입력 여부 확인 //
                const checkedLolNickname = lolNickname.trim().length === 0;
                if (checkedLolNickname) {
                    setLolNicknameError(true);
                    setLolNicknameErrorMessage('닉네임을 입력해주세요.');
                }
                // description: 태그 입력 여부 확인 //
                const checkedTag = tag.trim().length === 0;
                if (checkedTag) {
                    setTagError(true);
                    setTagErrorMessage('태그를 입력해주세요.');
                }

                // description: 개인정보동의 여부 확인 //
                if (!consent) setConsentError(true);

                if (checkedLolNickname || checkedTag || !consent) return;

                const requestBody: SignUpRequestDto = { //DTO 다

                    email,
                    password,
                    lolNickname,
                    tag,
                    agreedPersonal: consent  //이건 agreedPersonal: consent로 쓰는 이유는 내 서버엔티티가  agreedPersonal로 되어있어서
                };

                signUpRequest(requestBody).then(signUpResponse);
            }

            //          render: sign up 카드 컴포넌트 렌더링         //
            return (
                <div className='auth-card'>
                    <div className='auth-card-top'>
                        <div className='auth-card-title-box'>
                            <div className='auth-card-title'>{'회원가입'}</div>
                            <div className='auth-card-title-page'>{`${page}/2`}</div>
                        </div>
                        {page === 1 && (<>

                            <InputBox label='이메일 주소*' type='text' placeholder='이메일 주소를 입력해주세요.' value={email}
                                      setValue={setEmail} error={emailError} errorMessage={emailErrorMessage}/>
                            <InputBox label='비밀번호*' type={passwordType} placeholder='비밀번호를 입력해주세요.' value={password} setValue={setPassword} icon={passwordIcon} error={passwordError} errorMessage={passwordErrorMessage} onButtonClick={onPasswordIconClickHandler} />

                            <InputBox label='비밀번호 확인*' type={passwordCheckType} placeholder='비밀번호를 다시 입력해주세요.' value={passwordCheck} setValue={setPasswordCheck} icon={passwordCheckIcon} error={passwordCheckError} errorMessage={passwordCheckErrorMessage} onButtonClick={onPasswordCheckIconClickHandler} />
                        </>)}
                        {page === 2 && (<>

                            <InputBox label='롤 닉네임*' type='text' placeholder='롤 닉네임을 입력해주세요.' value={lolNickname}
                                      setValue={setLolNickname} error={lolNicknameError}
                                      errorMessage={lolNicknameErrorMessage}/>
                            <InputBox label='태그 *' type='text' placeholder='태그 를 입력해주세요.' value={tag}
                                      setValue={setTag} error={tagError} errorMessage={tagErrorMessage}/>
                        </>)}
                    </div>
                    <div className='auth-card-bottom'>
                        {page === 1 && (
                            <div className='auth-button' onClick={onNextStepButtonClickHandler}>{'다음 단계'}</div>
                        )}
                        {page === 2 && (<>
                            <div className='auth-consent-box'>
                                <div className='auth-check-box' onClick={onConsentCheckHandler}>
                                    {consent ? (<div className='check-round-fill-icon'></div>) : (
                                        <div className='check-ring-light-icon'></div>)}
                                </div>
                                <div
                                    className={consentError ? 'auth-consent-title-error' : 'auth-consent-title'}>{'개인정보동의'}</div>
                                <div className='auth-consent-link'>{'더보기>'}</div>
                            </div>
                            <div className='auth-button' onClick={onSignUpButtonClickHandler}>{'회원가입'}</div>
                        </>)}
                        <div className='auth-description-box'>
                            <div className='auth-description'>
                                {'이미 계정이 있으신가요? '}
                                <span className='description-emphasis' onClick={onSignInLinkClickHandler}>{'로그인'}</span>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
        return (    // render 인증화면 컴포넌트

            <div id='auth-wrapper'>
                <div className='auth-container'>
                    <div className='auth-jumbotron-box'>
                        <div className='auth-jumbotron-contents'>
                            <div className='jumbotron-icon'></div>
                            <div className='auth-jumbotron-text-box'>
                                <div className='auth-jumbotron-text'>{'환영합니다.'}</div>
                                <div className='auth-jumbotron-text'>{'GGWP 입니다.'}</div>
                            </div>
                        </div>
                    </div>
                    { view === 'sign-in' && <SignInCard /> }
                    { view === 'sign-up' && <SignUpCard /> }
                </div>
            </div>
        );

}