import Axios from 'axios';

// This wraps an Axios cancel token to support cancelable operations

export default class CancelableOperation {
    constructor() {
        let ct = Axios.CancelToken;
        this.source = ct.source();
    }

    cancel(){
        this.source.cancel();
    }
}