import gsApi from "./axios.config.ts";

export const signUp = async (username: string, password: string) => {
    try {
        await gsApi.post('user/signup', { name: username, password: password });
    } catch (error) {
        console.error("Error signing up:", error);
        throw error;
    }
};

export const login = async (username: string, password: string) => {
    try {
        const response = await gsApi.post('user/login', {
            name: username,
            password: password
        });
        return response.data.token;  // Return the JWT token
    } catch (error) {
        console.error("Error logging in:", error);
        throw error;
    }
};