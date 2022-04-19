import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';

export default class CommentsList extends Component{
    
    state = {
        comments: [],
        animalID : this.props.animalID,
        toggleStudent: this.props.toggleStudent,
        currUserType: this.props.usertype,
        currUserID: localStorage.getItem("userID"),
    };

    //get all comments for one animal
    componentDidMount(){
        console.log(this.state.toggleStudent)
        axios.get('http://localhost:8080/app/comments/animal/'+this.state.animalID).then(
            res => {
                console.log(res);
                this.setState({comments: res.data})
            }
        )

        console.log("this.props.usertype = "+this.props.usertype)
    }
    

    //Any user who is not a student or if the comment card belong to the current student user
    //the user can then click on the comment card and delete the comment
    cardLink2(comment){
        var authorID = comment.authorID.toString()
        var commentID = comment.commentID.toString()

        if (this.state.currUserType !== "Student" || this.state.currUserID === authorID){
            return <a href={"/comments/single?commentID="+commentID}>
                {comment.firstName} {comment.lastName}, {comment.userType}
                </a>
        }else{
            return <a href="#">
                {comment.firstName} {comment.lastName}, {comment.userType}
                </a>
        }
    }


    render(){
        return(
            <div className="overflow-auto">
            {/* {Create a card for each comment} */}
            {this.state.comments.map(comment => 
                //If the comment belongs to student, display card as Orange, else display card as grey
                (comment.userType === "Student" ) ?
                //If toggle students is on then no student comments are displayed
                (this.state.toggleStudent ?
                <div className="card text-white bg-warning mx-5 my-3" key={comment.commentId} style={{width: "50rem"}}>
                    <div className="card-header" >
                        {this.cardLink2(comment)}
                    </div>
                    <div className="card-body"  >
                        <p className="card-text">
                            {comment.message}
                        </p>
                        <p className="card-text">
                            <small className="text-muted-white">{comment.timestamp}</small>
                        </p>
                    </div>
                </div>:null):
                <div className="card bg-light mx-5 my-3" key={comment.commentID} style={{width: "50rem"}}>
                <div className="card-header" >
                    {this.cardLink2(comment)}
                </div>
                <div className="card-body"  >
                    <p className="card-text">
                        {comment.message}
                    </p>
                    <p className="card-text">
                        <small className="text-muted">{comment.timestamp}</small>
                    </p>
                </div>
            </div>

                )
            }
            </div>
        )

    }


}