import axios from "./axios_instance";

/**
 * 获取实验统计数据
 * @param {number} experimentId 实验ID
 * @returns {Promise} 包含实验统计数据的Promise
 */
export function getExperimentStats(experimentId) {
    return axios.get("/check/stats", {
        params: { experimentId }
    });
}

/**
 * 获取实验提交趋势数据
 * @param {number} experimentId 实验ID
 * @returns {Promise} 包含提交趋势数据的Promise
 */
export function getSubmissionTrend(experimentId) {
    return axios.get("/check/submissionTrend", {
        params: { experimentId }
    });
}

/**
 * 获取实验分数分布数据
 * @param {number} experimentId 实验ID
 * @returns {Promise} 包含分数分布数据的Promise
 */
export function getScoreDistribution(experimentId) {
    return axios.get("/check/scoreDistribution", {
        params: { experimentId }
    });
}

/**
 * 获取学生文档数
 * @param {number} experimentId 实验ID
 * @returns {Promise} 包含文档数的Promise
 */
export function getStudentDocumentCount(experimentId) {
    return axios.get("/check/documentCount", {
        params: { experimentId }
    });
}

/**
 * 获取所有实验列表（使用现有的experiment/list接口）
 * @returns {Promise} 包含实验列表的Promise
 */

export function listExperimentsForCheck() {
    return axios.get("/experiment/list", {

        params: {
            keyword: '',
            page: 1,
            size: 100
        }
    });
}

/**
 * 下载教师材料
 * @param {number} experimentId 实验ID
 * @returns {Promise} 包含文件下载流的Promise
 */
export function downloadMaterials(experimentId) {
    return axios.get("/document/download", {
        params: { experimentId },
        responseType: 'blob'
    });
}

export function getExperimentStudents(experimentId, page = 1, size = 10) {
    return axios.get("/experimentStudent/listByExperiment", {
        params: { experimentId, page, size }
    });
}

export function getDocumentStats(experimentId) {
    return axios.get("/document/stats", {
        params: { experimentId }
    });
}

export function exportExperimentData(experimentId) {
    return axios.get("/experiment/export", {
        params: { experimentId },
        responseType: 'blob'
    });
}

export function compareExperimentsProgress(experimentIds) {
    return axios.get("/experiment/compareProgress", {
        params: { experimentIds: experimentIds.join(',') }
    });
}

export function detectExperimentAnomalies(experimentId) {
    return axios.get("/experiment/detectAnomalies", {
        params: { experimentId }
    });
}