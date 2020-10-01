package ir.androidcourse.samangi.bmicalculator

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import java.text.DecimalFormat

class Programme() : Fragment() {
    var Sweight: Spinner? = null
    var Sheight: Spinner? = null
    var weight: EditText? = null
    var height: EditText? = null
    var result: EditText? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_programme, container, false)
        InitializeText(view)
        val calculate = view.findViewById<View>(R.id.BtnCalculate) as Button
        calculate.setOnClickListener(View.OnClickListener {
            if (IsValid()) {
                val weight = ConvertWeight()
                val height = ConvertHeight()
                val df = DecimalFormat("#.##")
                result!!.setText(df.format(Result(weight, height)).toString())
            }
        })

        val info = view.findViewById<View>(R.id.BtnInfo) as Button
        info.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (result!!.text.toString().trim { it <= ' ' } === "") return
                var str_Categories: String = ""
                val d_BMI = result!!.text.toString().trim { it <= ' ' }.toDouble()
                if (d_BMI < 18.50) {
                    str_Categories = "Underweight"
                } else if (d_BMI >= 18.50 && d_BMI < 24.99) {
                    str_Categories = "Normal healthy weight"
                } else if (d_BMI >= 25.00 && d_BMI < 29.99) {
                    str_Categories = "Overweight"
                } else if (d_BMI >= 30.0 && d_BMI < 39.99) {
                    str_Categories = "Obese"
                } else if (d_BMI >= 40.00) {
                    str_Categories = "Morbidly obese"
                }
                AlertDialog.Builder(activity).setTitle("Body Mass Index (BMI) Calculator").setMessage(str_Categories).setNeutralButton("Close", null).show()
            }
        })
        return view
    }

    private fun ConvertHeight(): Float {
        var result: Float = 0.0f
        var heightResult = 0.0f
        val unit = Sheight!!.selectedItem.toString()
        when (unit) {
            "ft" -> {
                heightResult = height!!.text.toString().toFloat()
                result = heightResult * 12.0f
            }
            "cm" -> {
                heightResult = height!!.text.toString().toFloat()
                result = heightResult * 0.3937f
            }
            else -> result = 0.0f
        }
        return result
    }

    private fun ConvertWeight(): Float {
        var result: Float = 0.0f
        val unit = Sweight!!.selectedItem.toString()
        when (unit) {
            "kg" -> {
                val weightResult = weight!!.text.toString().toFloat()
                result = weightResult * 2.2f
            }
            "lbs" -> result = weight!!.text.toString().toFloat()
            else -> result = 0.0f
        }
        return result
    }

    private fun Result(weight: Float, height: Float): Float {
        var result: Float = 0.0f
        val resultWeight: Float
        var resultHeight: Float
        resultWeight = weight * 0.45f
        resultHeight = height * 0.025f
        resultHeight = resultHeight * resultHeight
        result = resultWeight / resultHeight
        return result
    }

    private fun IsValid(): Boolean {
        val result = true
        if (weight!!.text.toString().trim { it <= ' ' }.length == 0) {
            weight!!.error = "Enter your weight"
            return false
        }
        if (height!!.text.toString().trim { it <= ' ' }.length == 0) {
            height!!.error = "Enter your height"
            return false
        }
        return result
    }

    private fun InitializeText(view: View) {
        Sweight = view.findViewById<View>(R.id.SpinnerWeight) as Spinner
        Sheight = view.findViewById<View>(R.id.SpinnerHeight) as Spinner
        weight = view.findViewById<View>(R.id.TxtWeight) as EditText
        height = view.findViewById<View>(R.id.TxtHeight) as EditText
        result = view.findViewById<View>(R.id.TxtResult) as EditText
    }
}