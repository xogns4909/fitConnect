import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  withCredentials : true
});

axiosInstance.interceptors.response.use(
    response => response,
    error => {
      if (error.response && error.response.status === 401) {
        window.location.href = '/login';
      }
      return Promise.reject(error);
    }
);

export default axiosInstance;
