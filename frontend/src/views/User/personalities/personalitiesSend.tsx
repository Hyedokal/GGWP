import axios from 'axios';
import { useCookies } from "react-cookie";
import { useState } from "react";

const RequestSender: React.FC = () => {
    const [cookies] = useCookies(['accessToken']); // accessToken을 쿠키에서 가져옵니다.
    const token = cookies.accessToken; // 토큰에 접근합니다.
    const [modalIsOpen, setModalIsOpen] = useState(false); // 모달 열림 여부를 상태로 관리합니다.
    const [inputValues, setInputValues] = useState({
        personality1: "",
        personality2: "",
        personality3: ""
    }); // 인풋 박스의 값을 상태로 관리합니다.

    const openModal = () => {
        setModalIsOpen(true); // 모달을 열기 위해 상태를 변경합니다.
    };

    const closeModal = () => {
        setModalIsOpen(false); // 모달을 닫기 위해 상태를 변경합니다.
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setInputValues(prevState => ({
            ...prevState,
            [name]: value
        })); // 입력된 값을 상태에 업데이트합니다.
    };

    const sendRequest = async () => {
        if (!token) {
            console.error('접근 토큰을 찾을 수 없습니다.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8000/member-service/v1/member/personalities', {
                personalities: [inputValues.personality1, inputValues.personality2, inputValues.personality3]
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // 헤더에 토큰을 포함시킵니다.
                }
            });

            console.log(response.data);
            closeModal();
            //페이지 리로드
            window.location.reload();

        } catch (error) {
            console.error('요청을 보내는 중 오류가 발생했습니다:', error);
        }
    };

    return (
        <div className="p-4">
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={openModal}>성격 추가</button>
            {modalIsOpen && (
                <div className="mt-4">
                    <input type="text" name="personality1" value={inputValues.personality1} onChange={handleInputChange} className="border border-gray-300 rounded-md px-2 py-1" />
                    <input type="text" name="personality2" value={inputValues.personality2} onChange={handleInputChange} className="border border-gray-300 rounded-md px-2 py-1 mt-2" />
                    <input type="text" name="personality3" value={inputValues.personality3} onChange={handleInputChange} className="border border-gray-300 rounded-md px-2 py-1 mt-2" />
                    <div className="mt-2">
                        <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-2" onClick={sendRequest}>전송</button>
                        <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" onClick={closeModal}>닫기</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default RequestSender;