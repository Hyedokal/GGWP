import React, { useEffect, useState } from "react";
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useCookies } from "react-cookie";
import UserInfoStore from "../../stores/userInfo.store";

interface Message {
    senderName: string;
    senderTag: string;
    receiverName: string;
    receiverTag: string;
    createdAt: string;
    msg: string;
}

export default function Test() {
    const { userInfo } = UserInfoStore();

    const [messages, setMessages] = useState<Message[]>([]);
    const [stompClient, setStompClient] = useState<Client | null>(null);
    const [cookies] = useCookies(["accessToken"]); // Assuming accessToken is the name of your cookie

    // Connect to WebSocket
    useEffect(() => {
        const socket = new SockJS('http://localhost:8000/ws-stomp');
        const client = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                "token": cookies.accessToken, // Send token as part of connection headers if needed
            },
            onConnect: () => {
                console.log('Connected to WebSocket');
                const subscriptionPath = `/sub/${userInfo?.lolNickname}#${userInfo?.tag}`;
                client.subscribe(subscriptionPath, message => {
                    console.log('Received message', message);
                    const receivedMessage: Message = JSON.parse(message.body);
                    setMessages(prevMessages => [...prevMessages, receivedMessage]);
                });
            },
            onDisconnect: () => {
                console.log('Disconnected from WebSocket');
            },
            // Add other STOMP client configuration as needed
        });

        client.activate();
        setStompClient(client);

        // Cleanup on component unmount
        return () => {
            client.deactivate();
        };
    }, [userInfo, cookies.accessToken]);

    // Placeholder function to send messages (modify as needed)
    const sendMessage = (msg: string) => {
        if (stompClient && stompClient.connected) {
            const sendPath = `/pub/${userInfo?.lolNickname}/${userInfo?.tag}`;
            stompClient.publish({ destination: sendPath, body: msg });
        }
    };

    // Render messages
    return (
        <div>
            <h2>Messages</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>{message.senderName}: {message.msg}</li>
                ))}
            </ul>
            {/* Add more UI components as needed */}
        </div>
    );
}