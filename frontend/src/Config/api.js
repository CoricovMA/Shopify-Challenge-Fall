import axios from 'axios'

const mainUrl = "http://localhost:8080/";

export function uploadImages(files){
    let formData = new FormData();

    files.forEach(
        file => {
            formData.append("files", file);
        }
    )

    return axios.post(`${mainUrl}upload`, formData,
        {
            headers: {
                "Content-Type": "multipart/form-data; boundary=----------287032381234132612",
                "Access-Control-Allow-Origin": "*"
            }
        }
    )

}

export function deleteImage(){

}