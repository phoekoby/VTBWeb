
class User {

    constructor(args) {
        if(!args.id || !args.email){
            throw new Error('No user data')
        }
        this.id = args.id
        this.firstname = args.firstname ? args.firstname : ''
        this.lastname = args.lastname ? args.lastname : ''
        this.email = args.email

        this.setNickname()
    }

    setNickname = () => {
        if(this.firstname){
            if(this.lastname) this.nickname = this.firstname + ' ' + this.lastname
            else this.nickname = this.firstname
        }else{
            this.nickname = this.email
        }
    }

    getNickname = () => this.nickname

}
