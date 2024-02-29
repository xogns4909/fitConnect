import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

function RequireAuth({ children }) {
  const auth = useAuth();

  if (!auth.isLoggedIn){
    return <Navigate to="/login" />;
  }

  return children;
}

export default RequireAuth;