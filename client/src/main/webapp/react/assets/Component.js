
class Component {

    constructor(args) {
        this.el = args.app.el
        this.app = args.app
        this.listeners = []
    }

    render = () => Promise.resolve()

    addListeners = () => {}

    getListeners = () => this.listeners

    getDynamicContent = () => this.el.querySelector('[dynamic-page-content="' + this.app.page + '"]')

}
