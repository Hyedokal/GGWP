import {useEffect, useState} from "react";
import {UserInfo} from "../../types";
import SockJS from 'sockjs-client';
import {Client, Stomp} from '@stomp/stompjs';
import {useCookies} from "react-cookie";


interface Message {
        senderName: string;
        senderTag: string;
        receiverName: string;
        receiverTag: string;
        createdAt: string;
        msg: string;
    }

    export default function Test() {
        const [userInfo, setUserInfo] = useState<UserInfo>({ email: '', lolNickname: '', tag: '', role: '', profileImage: '' });
        const [messages, setMessages] = useState<Message[]>([]);
        const [stompClient, setStompClient] = useState<Client | null>(null);
        const [isTokenValid, setIsTokenValid] = useState(true); // Assuming you have a way to determine this
        const [cookies, setCookie] = useCookies(); // state: Cookie state //



        useEffect(() => {
            const socket = new SockJS('http://localhost:8000/ws-stomp'); // Adjust URL as needed
            const client = new Client({
                brokerURL: socket.url,
                webSocketFactory: () => socket,
                onConnect: frame => {
                    console.log('Connected: ' + frame);

                    client.subscribe(`/sub/${userInfo.lolNickname}#${userInfo.tag}`, message => {
                        console.log('Received: ' + message.body);
                        //interface msg console



                        const receivedMessage: Message = JSON.parse(message.body);
                        setMessages(prevMessages => [...prevMessages, receivedMessage]);
                    });
                },
            });

            client.activate();
            setStompClient(client);

            const tokenExpiryCheckInterval = setInterval(() => {
                if (!checkTokenValidity()) {
                    setIsTokenValid(false);
                }
            }, 60000); // check every minute

            return () => {
                clearInterval(tokenExpiryCheckInterval);
                if (client) {
                    client.deactivate();
                }
            };
        }, []);

        useEffect(() => {
            if (!isTokenValid && stompClient) {
                disconnect();
            }
        }, [isTokenValid, stompClient]);

        function disconnect() {
            if (stompClient !== null) {
                stompClient.deactivate();
            }
            console.log("Disconnected");
        }

        function checkTokenValidity() {
            const token = cookies.accessToken;
            if (!token) return false;

            const payloadBase64 = token.split('.')[1];
            if (!payloadBase64) return false;

            const decodedJson = atob(payloadBase64);
            const decoded = JSON.parse(decodedJson);
            const exp = decoded.exp;

            if (!exp) return false;

            const currentTime = Date.now() / 1000;
            return exp > currentTime;
        }
        return (
            <div>
                <div>
                    <h2>Messages</h2>
                    <ul>
                        {messages.map((message, index) => (
                            <li key={index}>
                                {message.senderName}: {message.msg}
                            </li>
                        ))}
                    </ul>
                    {/* Add user input and send button here if required */}
                </div>
            </div>
        );
    }