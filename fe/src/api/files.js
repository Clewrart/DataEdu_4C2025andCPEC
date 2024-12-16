import axios from './axios_instance';

export function getFile(fileName) {
    axios.get(`/files?fileName=${fileName}`, {
        responseType: 'blob', 
    }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));

        const link = document.createElement('a');
        link.href = url;

        link.setAttribute('download', fileName);

        document.body.appendChild(link);

        link.click();

        link.parentNode.removeChild(link);
    }).catch(error => {
        console.error('Error downloading the file:', error);
    });
}
