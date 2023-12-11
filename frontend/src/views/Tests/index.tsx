import {useNavigate, useParams} from "react-router-dom";
import UserInfoStore from "../../stores/userInfo.store";
import {useCookies} from "react-cookie";
import React, {useState} from "react";
import SquadResponseDto from "../../apis/dto/response/squad/SquadResponseDto";
import InputBox from "../../components/InputBox";

export default function Test() {


    return (


        <div>
            <div className="bg-[#232a38] text-white p-4 rounded-lg w-[591px]">
                <div className="flex justify-between items-center mb-4">
                    <h1 className="text-lg font-bold mr-2">Nickname+Tag</h1>
                </div>
                <div className="border-b border-[#3a4253] py-2">
                    <div className="flex justify-between items-center">
                        <div>
                            <h2 className="text-sm">티어</h2>
                            <p className="text-sm text-[#8da9c4]">Silver 3</p>
                        </div>
                        <div>
                            <h2 className="text-sm">이전 시즌 티어</h2>
                            <p className="text-sm text-[#8da9c4]">Silver 4</p>
                        </div>
                    </div>
                </div>
                <div className="py-2">
                    <div className="flex justify-between items-center">
                        <div>
                            <h2 className="text-sm">포지션</h2>
                            <div>
                                <p className="text-sm text-[#8da9c4]">Write dummy text</p>
                            </div>
                        </div>
                        <div>
                            <h2 className="text-sm">찾는 포지션</h2>
                            <div>
                                <p className="text-sm text-[#8da9c4]">Write dummy text</p>
                            </div>
                        </div>
                        <button className="text-sm bg-[#3a4253] px-2 py-1 rounded">SOLO_RANK</button>
                    </div>
                </div>
                <div className="py-2">
                    <h2 className="text-sm mb-2">최근 전투 결과</h2>
                    <div className="flex items-center">
                        <div className="flex flex-col items-center">
                            <div className="w-8 h-8 rounded-full bg-[#3a4253]"></div>
                            <p className="text-xs text-[#8da9c4]">승리</p>
                    </div>
                        <div className="flex flex-col items-center">
                            <div className="w-8 h-8 rounded-full bg-[#3a4253]"></div>
                            <p className="text-xs text-[#8da9c4]">승리</p>
                        </div>
                        <div className="flex flex-col items-center">
                            <div className="w-8 h-8 rounded-full bg-[#3a4253]"></div>
                            <p className="text-xs text-[#8da9c4]">패배</p>
                        </div>
                </div>
                <div className="py-2">
                    <h2 className="text-sm mb-2">메모</h2>
                    <p className="text-xs text-[#8da9c4]">부가 설명이 필요합니다.</p>
                </div>
            </div>
        </div>
        </div>
    );
}
