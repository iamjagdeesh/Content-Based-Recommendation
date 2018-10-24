import React, { Component } from 'react';
import './App.css';
import { Collapse, Button, CardBody, Card, Container, Row, Col, CardTitle, CardSubtitle, CardText } from 'reactstrap';

import Recommendation from './models/recommendation.js';

class App extends Component {

  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);

    let collapseTemp = []
    for (let i = 0; i < 2; i++) {
      collapseTemp[i] = false
    }
    this.state = { collapse: collapseTemp, posts: [], data: [], isPostsLoading: true, isDataLoading: true };
    this.recommendation = new Recommendation();
    this.getRecommendationData = this.getRecommendationData.bind(this);
  }

  // componentDidMount() {
  //   fetch('/lucene/recommend')
  //     .then(response => {
  //       console.log(response);
  //       return response.json();
  //     })
  //     .then((data) => { 
  //       console.log(data);
  //       this.setState({ data: data, isDataLoading: true }); 
  //     });
  // }

  async getRecommendationData(e) {
    e.preventDefault();
    if (this.state.isDataLoading) {
      let data;
      data = await this.recommendation.getRecommendation();
      this.setState({data: data, isDataLoading: false});
    }
    if(this.state.isPostsLoading) {
      let posts;
      posts = await this.recommendation.getPosts();
      this.setState({posts: posts, isPostsLoading: false});
    }
    return
  }

  toggle(postNumber) {
    let collapseTemp = this.state.collapse
    collapseTemp[postNumber] = !collapseTemp[postNumber]
    this.setState({collapse: collapseTemp})
  }

  render() {
    return (
      <div>
        <Container>
          <Row justify="center">
              <Col md={{size: 2, offset: 4}}>
              <Button onClick={this.getRecommendationData}>Load Recommendations</Button>
              </Col>
          </Row>
          {
            !this.state.isDataLoading &&
            this.state.data.map((recRes, index) => {
              return (
                      <Row>
                      {/* <Button color="primary" onClick={() => this.toggle(index)} style={{ marginBottom: '1rem' }}>Post {index+1}</Button> */}
                      <Card>
                        <CardBody>
                          <CardTitle>Post {index+1}</CardTitle>
                          <CardText>{this.state.posts[index]}</CardText>
                          <Button onClick={() => this.toggle(index)}>Click to view recommendations</Button>
                        </CardBody>
                      </Card>
                      <Collapse isOpen={this.state.collapse[index]}>
                      {
                        recRes.map((rec, i) => {
                          return (
                            <Card>
                              <CardBody>
                              {rec}
                              </CardBody>
                            </Card>
                          )
                        })
                      }
                      </Collapse>
                      </Row>
              )
            })
          }
        </Container>
      </div>
    );
  }
}

export default App;
