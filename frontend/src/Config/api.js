import axios from 'axios'

export const mainUrl = "http://localhost:8080";

function uploadImages(files){
    let formData = new FormData();

    files.forEach(
        file => {
            formData.append("files", file);
        }
    )

    return axios.post(`${mainUrl}/upload`, formData,
        {
            headers: {
                "Content-Type": "multipart/form-data; boundary=----------287032381234132612",
                "Access-Control-Allow-Origin": "*"
            }
        }
    )

}

function deleteImage(filename){
    return axios.delete(`${mainUrl}/picture/${filename}`);
}

function getImage(filename){
    return axios.get(`${mainUrl}/picture/${filename}`);
}

function getAllImages(){
    return axios.get(`${mainUrl}/pictures`);
}

export {uploadImages, deleteImage, getAllImages, getImage};