import CancelableOperation from './cancelable.js';
import Axios from 'axios';

export default class Recommendation extends CancelableOperation {
    
    async getRecommendation() {
        
        let data;
        try {
            let response = await Axios.get('/lucene/recommend');
            data = response.data;
        } catch (err) {
            if(!Axios.isCancel(err)){
                throw err;
            }
        }
        return data;

    }

    async getPosts() {
        
        let posts;
        try {
            let response = await Axios.get('/lucene/posts');
            posts = response.data;
        } catch (err) {
            if(!Axios.isCancel(err)){
                throw err;
            }
        }
        return posts;

    }
}