import {rerenderEntireTree} from "./render";
import axios from "axios";

let state = {

}

export let login = (username, password) => {
    return axios
        .post("http://localhost:8187/auth-service/signin", {
            username,
            password
        }).then(response => {
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data))
            }
            return response.data
        })
}

export let logout = () => {
    localStorage.removeItem("user")
}

export let register = (username, email, password) =>{
    return axios
        .post("http://localhost:8187/auth-service/signup", {
            username,
            email,
            password
        })
}

export let getCurrentUser = () => {
return JSON.parse(localStorage.getItem('user'))
}

export let getAuthHeader = () =>{
    const user = JSON.parse(localStorage.getItem('user'))
    if (user && user.accessToken){
        return {Authorization: 'Bearer' + user.accessToken}
    }else {
        return {}
    }
}

export let getPublicContent = () => {
    return axios.get("http://localhost:8189/catalog-service");
}

export let getUserContent = () => {
    return axios.get("http://localhost:8189/lk-service/user", { headers: getAuthHeader() });
}

export let getManagerContent = () => {
    return axios.get("http://localhost:8189/lk-service/manager", { headers: getAuthHeader() });
}

export let getAdminContent = () => {
    return axios.get("http://localhost:8189/lk-service/admin", { headers: getAuthHeader() });
}
export let addFilm = (title, imageUrlLink, premierYear, country,genre, director, description) => {
    return axios
        .post("http://localhost:8187/catalog-service/add", {
            title,
            imageUrlLink,
            premierYear,
            country,
            genre,
            director,
            description
        })
}


export let updateMessageHandler = (newText) => {
    state.dialogsPage.newMessageText = newText;
    rerenderEntireTree(state);
}
const UserService = {
    getCurrentUser
}
export default state;