
<form>
    <input type="text" name="login" placeholder="Логин" />
    <input type="password" name="password" placeholder="Пароль" />
    <button auth-action="auth">Войти</button>
</form>

<script>
    class PageAuth extends Component {

        render = () => {
            return new Promise((resolve, reject) => {

                resolve()

            })
        }

        addListeners = () => {
            const l = (e) => {
                let el = e.target
                let action = false
                while(el){
                    action = el.getAttribute('auth-action')
                    if(action) break
                    el = el.parentElement
                }
                if(action === 'auth'){
                    e.preventDefault()
                    const login = this.el.querySelector('input[name=login]')
                    const password = this.el.querySelector('input[name=password]')
                    window.auth.authorize(login.value, password.value).then(user => {
                        if(user){
                            alert('Вы авторизованы!')
                            window.changePage('index')
                        }else{
                            this.el.querySelectorAll('input').forEach(el => el.value = '')
                        }
                    })
                }
            }
            this.listeners.push(l)
        }

    }
</script>
