import axios from "./axios_instance"


export function participate(experimentId, studentId) {
    const formData = new FormData();
    formData.append("experimentId", experimentId);
    formData.append("studentId", studentId);
    return axios.post(
        "/experimentStudent",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    )
}


export function listItem(keyword, page, size) {
    return axios.get(
        "/experimentStudent/list",
        {
            params: {
                keyword,
                page,
                size
            }
        }
    )
}

export function listItemByStudentId(teacherId,keyword,page,size){
     return axios.get(
        "/experimentStudent/listByTeacherId",
        {
            params: {
                teacherId,
                keyword,
                page,
                size
            }
        }
    )
}


export function getItemById(id) {
    return axios.get("/experimentStudent", {
        params:{
            id
        }
    })
}

export function updateES(formData) {
    return axios.patch("/experimentStudent", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
}

export function getItemByExperimentIdAndStudentId(experimentId, studentId) {
    return axios.get("/experimentStudent/getItemByExperimentIdAndStudentId", {
        params: {
            experimentId,
            studentId
        }
    })
}

export function getRank(experimentId, studentId) {
    return axios.get("/experimentStudent/getRank", {
        params: {
            experimentId,
            studentId
        }
    })
}

export function rankList(experimentId) {
    return axios.get("/experimentStudent/rankList", {
        params: {
            experimentId,
        }
    })
}