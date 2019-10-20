package com.ciandt.book.seeker.ui_kit

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.ciandt.book.seeker.R
import com.ciandt.book.seeker.util.getSizeInDp
import com.ciandt.book.seeker.util.hideSoftInput
import com.ciandt.book.seeker.util.safeSetTextAppearance

class SearchableInputTextField : ConstraintLayout {

    private lateinit var editText: EditText
    private lateinit var textViewError: TextView

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setupView(attrs)
    }

    private fun setupView(attrs: AttributeSet? = null) {
        setupEditText()
        setupError()
        setupConstraints()

        attrs?.apply {
            val typedArray =
                context.obtainStyledAttributes(this, R.styleable.SearchableInputTextField)
            hint = typedArray.getString(R.styleable.SearchableInputTextField_inputHint)
            typedArray.recycle()
        }
    }

    private fun setupEditText() {
        editText = EditText(context)
        val params = LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT
        )
        editText.apply {
            background =
                ContextCompat.getDrawable(context, R.drawable.bg_searchable_input_light_gray)
            setOnEditorActionListener { _, action, _ ->
                when (action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        onEditorAction?.invoke(text.toString())
                    }
                }
                hideSoftInput()
                true
            }
            setPadding(
                getSizeInDp(R.dimen.margin_xxsmall),
                getSizeInDp(R.dimen.margin_xxsmall),
                getSizeInDp(R.dimen.margin_xxsmall),
                getSizeInDp(R.dimen.margin_xxsmall)
            )
            imeOptions = EditorInfo.IME_ACTION_DONE
            id = R.id.etSearchableInputTextField
            inputType = InputType.TYPE_CLASS_TEXT
            maxLines = 1
        }
        editText.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(context, R.drawable.ic_search_gray),
            null,
            null,
            null
        )

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                error = null
            }
        })

        addView(editText, params)
    }

    private fun setupError() {
        textViewError = TextView(context)
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, getSizeInDp(R.dimen.margin_xxsmall), 0, 0)
        textViewError.apply {
            safeSetTextAppearance(R.style.TextAppearance_Body)
            setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            id = R.id.tvErrorSearchableInputTextField
            visibility = View.GONE
        }
        addView(textViewError, params)
    }

    private fun setupConstraints() {
        val set = ConstraintSet()
        set.apply {
            clone(this@SearchableInputTextField)

            connect(editText.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(editText.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(editText.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            connect(textViewError.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(textViewError.id, ConstraintSet.TOP, editText.id, ConstraintSet.BOTTOM)

            applyTo(this@SearchableInputTextField)
        }
    }

    /** * Return the value of the field in string. */
    val value: String?
        get() = editText.text.toString()

    /** * Text that informs the hint label. */
    var hint: String? = null
        get() = editText.hint.toString()
        set(text) {
            editText.hint = text
            field = text
        }

    /** * Receive a callback when the user tap the enter button */
    var onEditorAction: ((query: String) -> Unit)? = null

    /** * Text that informs the error of the component. If null the text will be GONE */
    var error: String? = null
        get() = textViewError.text.toString()
        set(text) {
            textViewError.visibility =
                takeIf { text.isNullOrEmpty() }?.run { View.GONE } ?: View.VISIBLE
            textViewError.text = text
            field = text
        }
}