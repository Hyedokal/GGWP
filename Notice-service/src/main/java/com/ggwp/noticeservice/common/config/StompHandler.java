package com.ggwp.noticeservice.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
//            String token = accessor.getFirstNativeHeader("Authorization");
//            if (jwtProvider.validate(token) == null) {
//                try {
//                    throw new AccessDeniedException("");
//                } catch (AccessDeniedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            return message;
        }
        return message;
    }
}

