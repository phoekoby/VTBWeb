
{{ components/common/header }}
<div class="app-content container pt-5">
    {{content}}
</div>

<script>
    class App {

        constructor(args = {}) {
            this.el = document.createElement('div')
            this.el.classList.add('app')
            this.page = args.page ? args.page : 'index'
            this.listeners = []
        }

        render = () => {
            return new Promise((resolve, reject) => {
                const params = {}
                params['content'] = window.templates['pages/' + this.page]
                // TODO: отрисовка страницы с получением данных
                this.el.innerHTML = render('pages/App', params)
                resolve(this.el)

                if(window.pages[this.page]){
                    const pageEl = window.pages[this.page](this)
                    pageEl.render().then(() => {
                        pageEl.addListeners()
                        this.listeners.push(...pageEl.getListeners())
                    })
                }
            })
        }

        getListeners = () => this.listeners

    }
</script>
