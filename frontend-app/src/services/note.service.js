import axios from "axios";
import authHeader from './auth-header';

const API_URL = "http://localhost:8080/api/note/";

const list = () => {
    return axios.get(API_URL + "list", { headers: authHeader() }).then((response) => {return response.data});
};

const update = (id, text, status) => {
    return axios.post(API_URL + "updatenote", {text, status, id}, {headers: authHeader() }).then((response) => {return response.data});
};

const insert = (text) => {
    return axios.post(API_URL + "insertnote", {text}, {headers: authHeader() }).then((response) => {return response.data});
};

const deleteNote = (id) => {
    return axios.post(API_URL + "deletenote", {id}, {headers: authHeader() }).then((response) => {return response.data});
};

export default {
    list,
    update,
    insert,
    deleteNote
}