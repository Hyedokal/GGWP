import React, {ChangeEvent, useEffect, useRef, useState} from 'react';
import axios from "axios";
import {useCookies} from "react-cookie";
import UserInfoStore from "../../../stores/userInfo.store";

const ProfileImg = () => {
    const [profileImage, setProfileImage] = useState<string | null>(null);
    const fileInputRef = useRef<HTMLInputElement | null>(null);
    const [cookies] = useCookies(['accessToken']);
    const token = cookies.accessToken;
    const { userInfo, setUserInfo } = UserInfoStore();

    useEffect(() => {
        // Update local state when userInfo changes
        if (userInfo && userInfo.profileImage) {
            setProfileImage(userInfo.profileImage);
        }
    }, [userInfo]);


    const onProfileImageChangeHandler = async (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || event.target.files.length === 0) return;

        const file = event.target.files[0];
        try {
            // Upload the file
            const formData = new FormData();
            formData.append('file', file);
            const uploadResponse = await axios.post('http://localhost:8000/member-service/file/upload', formData);
            const uploadedImageUrl = uploadResponse.data;

            // Update the profile image
            const updateResponse = await axios.patch('http://localhost:8000/member-service/v1/member/profile-image', {
                profileImage: uploadedImageUrl
            }, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (updateResponse.status === 200) {
                // Ensure all userInfo fields are set
                if (userInfo) {
                    setUserInfo({
                        ...userInfo,
                        profileImage: uploadedImageUrl,
                    });
                }
            }
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };

    const onProfileImageClickHandler = () => {
        if (!fileInputRef.current) return;
        fileInputRef.current.click();
    };



    return (
        <div className="flex flex-col items-center p-4 bg-white dark:bg-gray-700 rounded-lg shadow">
            <div
                className="w-24 h-24 bg-gray-300 dark:bg-gray-500 rounded-full"
                onClick={onProfileImageClickHandler}
                style={{ backgroundImage: `url(${profileImage})`, backgroundSize: 'cover' }}
            >
                {!profileImage && (
                    <div className="w-full h-full flex items-center justify-center">
                        {/* Display a placeholder or icon here if no profile image */}
                    </div>
                )}
                <input
                    ref={fileInputRef}
                    type="file"
                    accept="image/*"
                    style={{ display: 'none' }}
                    onChange={onProfileImageChangeHandler}
                />
            </div>
            <div className="mt-4 text-lg font-semibold text-gray-900 dark:text-gray-100">프로필</div>
        </div>
    );
}

export default ProfileImg;