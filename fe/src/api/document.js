import axios from "./axios_instance"


export function uploadDocument(formData) {
    return axios.post(
        "/document",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    )
}

export function deleteDocument(id) {
    return axios.delete(
        "/document",
        {
            params: {
                id,
            }
        }
    )
}



export function getDocuments(experimentId,userId,role) {
    return axios.get(
        "/document/list",
        {
            params: {
                experimentId,
                userId,
                role
            }
        }
    )
}

export function getDocumentById(id) {
    return axios.get(
        "/document",
        {
            params: {
              id
            }
        }
    )
}

