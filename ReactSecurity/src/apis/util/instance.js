import axios from "axios";

export const instance = axios.create({
    baseURL: "http://localhost:8080",
    // 프로젝트가 처음 부터 끝까지 랜더링 될 때 처음 한 번
    headers: {
        Authorization: localStorage.getItem("accessToken"),  // 처음에는 null이 들어감
    }
});