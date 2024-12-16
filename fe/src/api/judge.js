import axios from "./axios_instance";


export function judge(judgeUrl, files) {
    const formData = new FormData();
    for (const file of files) {
        formData.append("files", file)
    }
    return axios.post(judgeUrl, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}