
<div class="row transaction-item">
    <div class="col-3">
        {{ hash }}
    </div>
    <div class="col-3">
        {{ status }}
    </div>
</div>


<script>
    class Transaction extends Component {

        constructor(args) {
            super(args)
            this.args = args
            console.log(args)

            const temp = document.createElement('div')
            temp.innerHTML = window.render('components/entity/transaction', args)
            this.el = temp.children[0]
        }

        render = () => {
            return this.el
        }

    }
</script>
