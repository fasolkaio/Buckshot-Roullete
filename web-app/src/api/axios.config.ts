import axios from 'axios';

const gsApi = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: false
});

export default gsApi;