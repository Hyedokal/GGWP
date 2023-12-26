import React, { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { AUTH_PATH } from "./constant"; // Ensure this path is correctly imported

interface ProtectedRouteProps {
    children: ReactNode;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
    const [cookies] = useCookies(['accessToken']);
    const accessToken = cookies.accessToken;

    if (!accessToken) {
        // Redirect to the login or authentication page
        return <Navigate to={AUTH_PATH} replace />;
    }

    return <>{children}</>;
};

export default ProtectedRoute;