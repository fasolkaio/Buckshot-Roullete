import axios from 'axios';

const gsApi = axios.create({
    baseURL: 'http://192.168.183.94:8080/api',
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: false
});

export default gsApi;