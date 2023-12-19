import {useNavigate, useParams} from "react-router-dom";
import UserInfoStore from "../../stores/userInfo.store";
import {useCookies} from "react-cookie";
import React, {useEffect, useState} from "react";
import SquadResponseDto from "../../apis/dto/response/squad/SquadResponseDto";
import InputBox from "../../components/InputBox";
import axios from "axios";
// Define interfaces for the response structure
interface Announce {
    title: string;
    content: string;
    updatedAt: string;
}

interface Pageable {
    pageNumber: number;
    pageSize: number;
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    offset: number;
    unpaged: boolean;
    paged: boolean;
}

interface AnnounceResponse {
    content: Announce[];
    pageable: Pageable;
    last: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}
export default function Test() {
    const [announces, setAnnounces] = useState<AnnounceResponse | null>(null);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        fetchAnnouncements(currentPage);
    }, [currentPage]);

    const fetchAnnouncements = (page: number) => {
        axios.post('http://localhost:8000/v1/announces/search', { page: page })
            .then(response => {
                setAnnounces(response.data);
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    };

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const renderPageNumbers = () => {
        const totalPages = announces?.totalPages ?? 0;
        const pages = [];
        for (let i = 0; i < totalPages; i++) {
            pages.push(
                <button key={i} onClick={() => handlePageChange(i)} disabled={i === currentPage}>
                    {i}
                </button>
            );
        }
        return pages;
    };

    return (
        <div>
            <h1>Announcements</h1>
            {announces && (
                <div>
                    {announces.content.map((announce, index) => (
                        <div key={index}>
                            <h3>{announce.id}</h3>
                            <h2>{announce.title}</h2>
                            <p>{announce.content}</p>
                            <small>Updated at: {announce.updatedAt}</small>
                        </div>
                    ))}
                </div>
            )}

            <div>
                <button onClick={() => handlePageChange(Math.max(currentPage - 1, 0))} disabled={currentPage === 0}>Before</button>
                {renderPageNumbers()}
                <button onClick={() => handlePageChange(Math.min(currentPage + 1, (announces?.totalPages ?? 1) - 1))} disabled={announces?.last ?? true}>After</button>
            </div>
        </div>
    );
}

