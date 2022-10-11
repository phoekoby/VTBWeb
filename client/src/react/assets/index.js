
document.addEventListener('DOMContentLoaded', event => {

    window.templates = {}
    document.querySelectorAll('[template]').forEach(script => {
        const template_name = script.getAttribute('template')
        templates[template_name] = script.innerHTML
    })
    window.pages = {
        index: el => new PageIndex({app: el}),
        auth: el => new PageAuth({app: el}),
        transactions: el => new PageTransactions({app: el}),
    }

    window.render = (template, params = {}) => {
        let text = templates[template]
        Object.keys(params).map((key, i) => {
            const re = new RegExp('\\{\\{\\s*' + key + '\\s*\\}\\}', 'g')
            console.log(re)
            text = text.replaceAll(re, params[key])
        })
        Object.keys(templates).map((key, i) => {
            const re = new RegExp('\\{\\{\\s*' + key + '\\s*\\}\\}', 'g')
            text = text.replaceAll(re, templates[key])
        })
        text = text.replaceAll(/\{\{[^}]*\}\}/g, '')
        return text
    }

    window.api = new Api()
    window.app = new App({page: 'index'})
    const appContainer = document.getElementById('App')

    window.renderApp = (page) => {
        const newApp = new App({page})
        newApp.render().then(el => {
            appContainer.innerHTML = ''
            appContainer.appendChild(el)
            window.app = newApp
        })
    }
    renderApp('index')

    window.changePage = (page = 'index') => window.renderApp(page)

    window.listeners = []
    window.listeners.push((e) => {
        let el = e.target
        let page = false
        while(el){
            page = el.getAttribute('page')
            if(page) break
            el = el.parentElement
        }
        if(page){
            changePage(page)
        }
    })

    document.addEventListener('click', e => {
        window.app.getListeners().map(l => l(e))
        window.listeners.map(l => l(e))
    })

    window.auth = new Auth()
    window.auth.checkAuth()

})
