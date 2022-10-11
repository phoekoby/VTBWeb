
<div class="h1">Транзакции</div>

<div dynamic-page-content="transactions"></div>

<script>
    class PageTransactions extends Component {

        render = () => {
            return new Promise((resolve, reject) => {

                this.getTransactions({size: 10})
                    .then(transactions => {
                        const elements = []
                        transactions.map(transaction => {
                            const t = new Transaction({...transaction, app: this.app})
                            elements.push(t.render())
                        })
                        resolve()
                        const dynamic_content = this.getDynamicContent()
                        if(dynamic_content){
                            dynamic_content.innerHTML = ''
                            dynamic_content.append(...elements)
                            console.log(elements)
                        }
                    })

            })
        }

        getTransactions = (args = {}) => {
            const page = Math.max(1, args.page * 1)
            const size = Math.max(1, Math.min(100, args.size))

            return new Promise((resolve, reject) => {
                // TODO: get transactions from api by page
                resolve([
                    {hash: 'abcdefghijklmnop', status: 'Выполняется'},
                    {hash: 'abcdefghijklmnop', status: 'Выполняется'},
                    {hash: 'abcdefghijklmnop', status: 'Выполняется'},
                    {hash: 'abcdefghijklmnop', status: 'Выполняется'},
                ])
            })
        }

    }
</script>
